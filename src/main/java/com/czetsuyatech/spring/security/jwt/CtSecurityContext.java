package com.czetsuyatech.spring.security.jwt;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Data
@AllArgsConstructor
public class CtSecurityContext implements Serializable {

  private String tokenString;
  private String idTokenString;
}
