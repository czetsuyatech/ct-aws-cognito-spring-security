package com.czetsuyatech.spring.security.exceptions;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public class InvalidIdTokenException extends RuntimeException {

  public InvalidIdTokenException(String msg) {
    super(msg);
  }
}
