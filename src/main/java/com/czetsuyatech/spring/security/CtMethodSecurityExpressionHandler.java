package com.czetsuyatech.spring.security;

import org.springframework.security.core.Authentication;

public interface CtMethodSecurityExpressionHandler {

  CtMethodSecurityExpressionRoot getExpressionRoot(Authentication authentication);
}
