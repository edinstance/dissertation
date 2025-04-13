package backend.admin.directives;

import backend.admin.dynamodb.AdminDynamoService;
import backend.common.config.logging.AppLogger;
import backend.common.dynamodb.tables.AdminLogs;
import backend.common.helpers.AuthHelpers;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDirective;
import com.netflix.graphql.types.errors.ErrorType;
import graphql.GraphQLError;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.FieldCoordinates;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This class is the admin directive.
 */
@DgsComponent
@DgsDirective(name = "admin")
public class AdminDirective implements SchemaDirectiveWiring {

  /**
   * The auth helpers to use.
   */
  private final AuthHelpers authHelpers;

  private final AdminDynamoService adminDynamoService;

  /**
   * The constructor for the admin directive.
   *
   * @param authHelpers the auth helpers object to use.
   */
  @Autowired
  public AdminDirective(AuthHelpers authHelpers, AdminDynamoService adminDynamoService) {
    this.authHelpers = authHelpers;
    this.adminDynamoService = adminDynamoService;
  }

  /**
   * This function intercepts the data fetcher of a field and applies
   * the admin checks.
   *
   * @param environment the graphql environment.
   * @return the original field definition and the modified field data fetcher.
   */
  @Override
  public GraphQLFieldDefinition onField(
          SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {

    GraphQLObjectType parentType = (GraphQLObjectType)
            environment.getFieldsContainer();

    GraphQLFieldDefinition fieldDefinition = environment.getFieldDefinition();

    String parentName = parentType.getName();
    String fieldName = fieldDefinition.getName();

    FieldCoordinates coordinates = FieldCoordinates.coordinates(parentName,
            fieldName);

    DataFetcher<?> originalDataFetcher = environment.getCodeRegistry()
            .getDataFetcher(coordinates, fieldDefinition);

    DataFetcher<?> dataFetcher = DataFetcherFactories.wrapDataFetcher(
            originalDataFetcher,
            (dataFetchingEnvironment, value) -> {
              // Check if the current user has admin privileges
              List<String> userGroups = authHelpers.getCurrentUserGroups();
              UUID userId = authHelpers.getCurrentUserId();

              AdminLogs logEntry = new AdminLogs(
                      userId,
                      UUID.randomUUID(),
                      parentName,
                      fieldName);


              HttpServletRequest request = getCurrentHttpRequest();
              if (request != null) {
                logEntry.setIpAddress(getIpAddressFromRequest(request));
                logEntry.setUserAgent(request.getHeader("User-Agent"));
              }

              if (userGroups == null || userGroups.isEmpty()
                      || !userGroups.contains("SubShopAdmin")) {

                AppLogger.warn("Unauthorized access attempt to admin-only field: " + fieldName);

                logEntry.setSuccess(false);
                adminDynamoService.writeAdminLog(logEntry);

                return DataFetcherResult.newResult()
                        .error(GraphQLError.newError().errorType(ErrorType.PERMISSION_DENIED)
                                .message("Access denied").build())
                        .build();
              }
              AppLogger.info("Admin access granted for field: " + fieldName);

              logEntry.setSuccess(true);
              adminDynamoService.writeAdminLog(logEntry);

              return value;
            }
    );

    environment.getCodeRegistry().dataFetcher(coordinates, dataFetcher);

    return fieldDefinition;
  }

  private HttpServletRequest getCurrentHttpRequest() {
    try {
      if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes) {
        return ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
      }
      AppLogger.info("No ServletRequestAttributes found in RequestContextHolder.");
      return null;
    } catch (IllegalStateException e) {
      AppLogger.warn("Could not get current HTTP request, "
              + "likely not in a request context: {}", e.getMessage());
      return null;
    }
  }

  private String getIpAddressFromRequest(HttpServletRequest request) {
    if (request == null) {
      return null;
    }
    // Prioritize X-Forwarded-For for load balancers/proxies
    String xffHeader = request.getHeader("X-Forwarded-For");
    if (xffHeader != null && !xffHeader.isEmpty()) {
      // Return the first IP in the list (client IP)
      return xffHeader.split(",")[0].trim();
    }
    // Fallback to remote address if header is missing
    return request.getRemoteAddr();
  }
}
