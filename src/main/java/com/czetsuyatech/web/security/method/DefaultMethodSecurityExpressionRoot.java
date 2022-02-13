package com.czetsuyatech.web.security.method;

import org.springframework.security.core.Authentication;

public class DefaultMethodSecurityExpressionRoot extends CtMethodSecurityExpressionRoot {

  public DefaultMethodSecurityExpressionRoot(Authentication authentication) {
    super(authentication);
  }
}
