package com.czetsuyatech.web.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface CtHttpSecurityConfigurer {

  public void configure(HttpSecurity http) throws Exception;
}
