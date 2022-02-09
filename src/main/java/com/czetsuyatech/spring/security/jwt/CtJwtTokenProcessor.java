package com.czetsuyatech.spring.security.jwt;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public interface CtJwtTokenProcessor {

  Authentication getAuthentication(HttpServletRequest request)
      throws Exception;
}