package com.czetsuyatech.web.security.exceptions;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public class InvalidJwtIssuerException extends RuntimeException {

  public InvalidJwtIssuerException(String msg) {
    super(msg);
  }
}