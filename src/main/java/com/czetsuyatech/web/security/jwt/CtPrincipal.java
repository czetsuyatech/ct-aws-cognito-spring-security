package com.czetsuyatech.web.security.jwt;

import java.io.Serializable;
import java.security.Principal;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Data
@RequiredArgsConstructor
public class CtPrincipal<T extends CtSecurityContext> implements Principal, Serializable {

  private final String name;
  private final T context;

  public T getCtSecurityContext() {
    return context;
  }
}