package com.example.fastChecking.config;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

  private final UserAuthProvider userAuthProvider;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    String path = request.getServletPath();
    if (path.equals("/login") || path.equals("/register")) {
      filterChain.doFilter(request, response);
      return;
    }

    if (header != null) {
      String[] authElements = header.split(" ");
      if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
        try {
          SecurityContextHolder.getContext()
              .setAuthentication(userAuthProvider.validateToken(authElements[1]));
        } catch (RuntimeException e) {
          SecurityContextHolder.clearContext();
          log.error(path + " " + e.getMessage());
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.setContentType("application/json");
          response.getWriter().write("{\"error\": \"Token invalid\"}");
          return;
        }
      }
    } else {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write("{\"error\": \"Authorization header missing\"}");
      return;
    }
    filterChain.doFilter(request, response);
  }

}
