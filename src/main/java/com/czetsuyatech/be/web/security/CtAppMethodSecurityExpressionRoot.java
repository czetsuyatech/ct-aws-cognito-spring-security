package com.czetsuyatech.be.web.security;

import com.czetsuyatech.web.security.method.DefaultMethodSecurityExpressionRoot;
import org.springframework.security.core.Authentication;

public class CtAppMethodSecurityExpressionRoot extends DefaultMethodSecurityExpressionRoot {

  public CtAppMethodSecurityExpressionRoot(Authentication authentication) {
    super(authentication);
  }

  public boolean isAuthorized() {
    return true;
  }

  public boolean isUnAuthorized() {
    return false;
  }
}
