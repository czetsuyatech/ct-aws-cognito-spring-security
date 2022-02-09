package com.czetsuyatech.spring.security.jwt;

import java.io.Serializable;
import java.security.Principal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CtPrincipal implements Principal, Serializable {

  private String name;
  private String token;
  private User user;

  public void setPrincipalData(String name, String token, User user) {
    this.name = name;
    this.token = token;
    this.user = user;
  }
}
