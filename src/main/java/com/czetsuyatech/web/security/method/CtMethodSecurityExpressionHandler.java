package com.czetsuyatech.web.security.method;

import org.springframework.security.core.Authentication;

public interface CtMethodSecurityExpressionHandler {

  CtMethodSecurityExpressionRoot getExpressionRoot(Authentication authentication);
}
