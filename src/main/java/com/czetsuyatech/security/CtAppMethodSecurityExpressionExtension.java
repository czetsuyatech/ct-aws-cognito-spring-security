package com.czetsuyatech.security;

import com.czetsuyatech.spring.security.DefaultMethodSecurityExpressionRoot;
import org.springframework.security.core.Authentication;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public class CtAppMethodSecurityExpressionExtension extends DefaultMethodSecurityExpressionRoot {

  public CtAppMethodSecurityExpressionExtension(Authentication authentication) {
    super(authentication);
  }

  public boolean isAuthorized() {
    return true;
  }

  public boolean isUnAuthorized() {
    return false;
  }
}
