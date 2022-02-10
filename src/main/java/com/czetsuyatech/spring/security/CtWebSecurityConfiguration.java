package com.czetsuyatech.spring.security;

import com.czetsuyatech.spring.security.jwt.CtJwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CtWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final CtHttpSecurityConfigurer httpSecurityConfig;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) {
    CtJwtAuthenticationProvider jwtAuthenticationProvider = new CtJwtAuthenticationProvider();
    auth.authenticationProvider(jwtAuthenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    httpSecurityConfig.configure(http);
  }
}
