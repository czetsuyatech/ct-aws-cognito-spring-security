package com.czetsuyatech.spring.security.filters;

import com.czetsuyatech.spring.security.jwt.CtJwtTokenProcessor;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Slf4j
public class CtJwtAuthenticationFilter extends OncePerRequestFilter implements ApplicationContextAware {

  private ApplicationContext applicationContext;
  private CtJwtTokenProcessor ctJwtTokenProcessor;

  public CtJwtAuthenticationFilter(CtJwtTokenProcessor ctJwtTokenProcessor) {
    this.ctJwtTokenProcessor = ctJwtTokenProcessor;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    Authentication authentication = null;
    try {
      authentication = ctJwtTokenProcessor.getAuthentication((HttpServletRequest) request);

      if (authentication != null) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
      }

    } catch (Exception e) {
      log.error("Error occurred while processing Cognito ID Token {}", e.getMessage());
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}