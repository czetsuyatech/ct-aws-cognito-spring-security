package com.czetsuyatech.spring.security.jwt;

import java.io.Serializable;
import java.security.Principal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CtPrincipal implements Principal, Serializable {

  private String name;

  public void setPrincipalData(String name) {
    this.name = name;
  }
}
