package com.czetsuyatech.spring.security.exceptions;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 * @since
 */
public class MissingAuthorizationHeaderException extends RuntimeException {

  public MissingAuthorizationHeaderException(String msg) {
    super(msg);
  }
}
