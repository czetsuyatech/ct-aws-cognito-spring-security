package com.czetsuyatech.web.security.identity;

import com.czetsuyatech.web.security.jwt.CtSecurityContext;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 * @since
 */
public interface OidcCtAccount extends CtAccount {

  CtSecurityContext getCtSecurityContext();
}
