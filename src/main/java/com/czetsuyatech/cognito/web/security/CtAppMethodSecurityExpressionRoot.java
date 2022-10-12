package com.czetsuyatech.cognito.web.security;

import com.czetsuyatech.spring.security.method.DefaultMethodSecurityExpressionRoot;
import lombok.Builder;
import lombok.Data;
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

  @Builder
  @Data
  public static class UserData {

    private final Long id;
  }
}
