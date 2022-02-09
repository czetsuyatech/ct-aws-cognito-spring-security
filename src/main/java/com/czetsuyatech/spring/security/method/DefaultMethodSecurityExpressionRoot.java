package com.czetsuyatech.spring.security.method;

import com.czetsuyatech.spring.security.method.CtMethodSecurityExpressionRoot;
import org.springframework.security.core.Authentication;

public class DefaultMethodSecurityExpressionRoot extends CtMethodSecurityExpressionRoot {

  public DefaultMethodSecurityExpressionRoot(Authentication authentication) {
    super(authentication);
  }
}
