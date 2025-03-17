package com.finalproject.backend.admin.directives;

import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDirective;
import com.netflix.graphql.types.errors.ErrorType;
import graphql.GraphQLError;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.FieldCoordinates;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

  @Autowired
  public AdminDirective(AuthHelpers authHelpers) {
    this.authHelpers = authHelpers;
  }

  @Override
  public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
    GraphQLObjectType parentType = (GraphQLObjectType) environment.getFieldsContainer();
    GraphQLFieldDefinition fieldDefinition = environment.getFieldDefinition();

    String parentName = parentType.getName();
    String fieldName = fieldDefinition.getName();

    FieldCoordinates coordinates = FieldCoordinates.coordinates(parentName, fieldName);

    DataFetcher<?> originalDataFetcher = environment.getCodeRegistry().getDataFetcher(coordinates, fieldDefinition);

    DataFetcher<?> dataFetcher = DataFetcherFactories.wrapDataFetcher(
            originalDataFetcher,
            (dataFetchingEnvironment, value) -> {
              // Check if the current user has admin privileges
              List<String> userGroups = authHelpers.getCurrentUserGroups();

              if (userGroups.isEmpty() || !userGroups.contains("SubShopAdmin")) {
                AppLogger.warn("Unauthorized access attempt to admin-only field: " + fieldName);
                return DataFetcherResult.newResult()
                        .error(GraphQLError.newError().errorType(ErrorType.PERMISSION_DENIED)
                                .message("Access denied").build())
                        .build();
              }
              AppLogger.info("Admin access granted for field: " + fieldName);
              return value;
            }
    );

    environment.getCodeRegistry().dataFetcher(coordinates, dataFetcher);

    return fieldDefinition;
  }
}
