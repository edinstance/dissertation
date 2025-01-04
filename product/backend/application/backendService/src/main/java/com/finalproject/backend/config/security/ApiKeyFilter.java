package com.finalproject.backend.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filter that checks for a valid API key in the request headers.
 */
public class ApiKeyFilter extends GenericFilterBean {

  /**
   * The valid API key.
   */
  private final String validApiKey;

  /**
   * Constructor that initializes the filter with the valid API key.
   *
   * @param apiKey The api key that should be used for authentication.
   */
  public ApiKeyFilter(final String apiKey) {
    this.validApiKey = apiKey;
  }

  /**
   * Filters incoming requests and checks for a valid API key.
   *
   * @param request  The incoming request.
   * @param response The outgoing response.
   * @param chain    The filter chain.
   * @throws IOException      If an input or output exception occurs.
   * @throws ServletException If a servlet exception occurs.
   */
  @Override
  public void doFilter(final ServletRequest request,
                       final ServletResponse response, final FilterChain chain
  ) throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    String apiKey = httpRequest.getHeader("X-API-KEY");

    try {
      if (apiKey != null) {
        if (validApiKey.equals(apiKey)) {
          Authentication auth = new
                  UsernamePasswordAuthenticationToken(
                  apiKey, null, Collections.emptyList()
          );

          SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
          throw new BadCredentialsException("Invalid API Key");
        }
      }
      chain.doFilter(request, response);
    } catch (BadCredentialsException ex) {
      // Set 401 status and include an error message in the response
      SecurityContextHolder.clearContext();
      httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      httpResponse.getWriter().write("Unauthorized: " + ex.getMessage());
    }
  }
}
