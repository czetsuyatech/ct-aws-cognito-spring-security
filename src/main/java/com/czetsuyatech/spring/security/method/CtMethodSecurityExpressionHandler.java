package com.czetsuyatech.spring.security.method;

import org.springframework.security.core.Authentication;

public interface CtMethodSecurityExpressionHandler {

  CtMethodSecurityExpressionRoot getExpressionRoot(Authentication authentication);
}
