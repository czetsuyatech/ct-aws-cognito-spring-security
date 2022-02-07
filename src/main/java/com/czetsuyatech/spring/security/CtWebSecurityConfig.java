package com.czetsuyatech.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CtWebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final CtHttpSecurityConfigurer httpSecurityConfig;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    super.configure(http);
    httpSecurityConfig.configure(http);
  }
}
