package com.czetsuyatech.spring.security.filters;

import com.czetsuyatech.spring.security.SecurityConstants;
import com.czetsuyatech.spring.security.jwt.CtJwtTokenProcessor;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public class CtAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

  private final CtJwtTokenProcessor ctJwtTokenProcessor;

  public CtAuthenticationProcessingFilter(final RequestMatcher requiresAuth,
      final CtJwtTokenProcessor ctJwtTokenProcessor) {
    super(requiresAuth);
    this.ctJwtTokenProcessor = ctJwtTokenProcessor;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {

    Authentication requestAuthentication = null;
    try {
      requestAuthentication = ctJwtTokenProcessor.getAuthentication((HttpServletRequest) httpServletRequest);

    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    return getAuthenticationManager().authenticate(requestAuthentication);

  }

  private String stripBearerToken(String token) {
    return token.startsWith(SecurityConstants.BEARER_PREFIX) ? token.substring(SecurityConstants.BEARER_PREFIX.length())
        : token;
  }

  @Override
  protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
      final FilterChain chain, final Authentication authResult) throws IOException, ServletException {

    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authResult);
    SecurityContextHolder.setContext(context);

    chain.doFilter(request, response);
  }
}