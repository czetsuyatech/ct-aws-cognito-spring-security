package com.czetsuyatech.spring.security.cognito;

import com.czetsuyatech.spring.security.jwt.CtJwtTokenProcessor;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Slf4j
public class CognitoJwtAuthenticationFilter extends OncePerRequestFilter {

  private CtJwtTokenProcessor ctJwtTokenProcessor;

  public CognitoJwtAuthenticationFilter(CtJwtTokenProcessor ctJwtTokenProcessor) {
    this.ctJwtTokenProcessor = ctJwtTokenProcessor;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    Authentication authentication = null;
    try {
      authentication = ctJwtTokenProcessor.getAuthentication((HttpServletRequest) request);

      if (authentication != null) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }

    } catch (Exception e) {
      log.error("Error occurred while processing Cognito ID Token {}", e.getMessage());
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
  }
}