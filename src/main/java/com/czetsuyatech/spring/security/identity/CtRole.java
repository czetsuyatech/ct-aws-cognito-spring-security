package com.czetsuyatech.spring.security.identity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Data
public class CtRole implements GrantedAuthority {

  private String role;

  public CtRole(String role) {
    Assert.notNull(role, "role cannot be null");
    this.role = role;
  }

  @Override
  public String getAuthority() {
    return role;
  }
}
