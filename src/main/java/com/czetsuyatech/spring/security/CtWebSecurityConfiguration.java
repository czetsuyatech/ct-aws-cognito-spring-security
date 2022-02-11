package com.czetsuyatech.spring.security;

import static com.nimbusds.jose.JWSAlgorithm.RS256;

import com.czetsuyatech.spring.security.config.CognitoJwtConfigData;
import com.czetsuyatech.spring.security.jwt.CognitoJwtTokenProcessor;
import com.czetsuyatech.spring.security.filters.CtAuthenticationProcessingFilter;
import com.czetsuyatech.spring.security.jwt.CtJwtTokenProcessor;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CtWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final CtHttpSecurityConfigurer httpSecurityConfig;
  private final CtAuthenticationProvider ctAuthenticationProvider;
  private final CtJwtTokenProcessor ctJwtTokenProcessor;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(ctAuthenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    httpSecurityConfig.configure(http);
    http.addFilterBefore(ctAuthenticationFilter(), AnonymousAuthenticationFilter.class);
  }

  @Override
  public void configure(WebSecurity webSecurity) throws Exception {
    webSecurity.ignoring().antMatchers("/token/**");
  }

  @Bean
  public CtAuthenticationProcessingFilter ctAuthenticationFilter()
      throws Exception {

    RequestMatcher protectedUrls = new OrRequestMatcher(new AntPathRequestMatcher("/api/**"));
    final CtAuthenticationProcessingFilter filter = new CtAuthenticationProcessingFilter(protectedUrls,
        ctJwtTokenProcessor);
    filter.setAuthenticationManager(authenticationManager());
    filter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());

    return filter;
  }
}