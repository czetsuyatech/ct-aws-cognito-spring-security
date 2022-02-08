package com.czetsuyatech.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@RestController
@Validated
@Slf4j
public class ApiTestController {

  @GetMapping("/hello")
  @ResponseStatus(HttpStatus.OK)
  public String hello() {
    return "hello world";
  }

  @GetMapping("/api/testing/authorized")
  @PreAuthorize("isAuthorized()")
  @ResponseStatus(HttpStatus.OK)
  public String authorized() {
    return "authorized";
  }

  @GetMapping("/api/testing/unauthorized")
  @PreAuthorize("isUnAuthorized()")
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public String unAuthorized() {
    return "unauthorized";
  }
}
