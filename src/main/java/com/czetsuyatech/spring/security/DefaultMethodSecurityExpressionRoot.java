package com.czetsuyatech.spring.security;

import org.springframework.security.core.Authentication;

public class DefaultMethodSecurityExpressionRoot extends CtMethodSecurityExpressionRoot {

  public DefaultMethodSecurityExpressionRoot(Authentication authentication) {
    super(authentication);
  }
}
