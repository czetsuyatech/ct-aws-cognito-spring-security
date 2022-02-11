package com.czetsuyatech.spring.security.identity;

import com.czetsuyatech.spring.security.identity.CtAccount;
import com.czetsuyatech.spring.security.jwt.CtSecurityContext;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 * @since
 */
public interface OidcCtAccount extends CtAccount {

  CtSecurityContext getCtSecurityContext();
}
