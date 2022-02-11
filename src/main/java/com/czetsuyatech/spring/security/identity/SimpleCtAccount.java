package com.czetsuyatech.spring.security.identity;

import com.czetsuyatech.spring.security.jwt.CtSecurityContext;
import java.io.Serializable;
import java.security.Principal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@AllArgsConstructor
@ToString
public class SimpleCtAccount implements OidcCtAccount, Serializable {

  private Principal principal;
  private Set<String> roles;
  private CtSecurityContext securityContext;

  @Override
  public Principal getPrincipal() {
    return principal;
  }

  @Override
  public Set<String> getRoles() {
    return roles;
  }

  @Override
  public CtSecurityContext getCtSecurityContext() {
    return securityContext;
  }
}
