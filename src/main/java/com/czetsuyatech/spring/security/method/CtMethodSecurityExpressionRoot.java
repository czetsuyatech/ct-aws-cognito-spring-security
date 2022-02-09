package com.czetsuyatech.spring.security.method;

import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public abstract class CtMethodSecurityExpressionRoot implements MethodSecurityExpressionOperations {

  private final Authentication authentication;

  private Object filterObject;
  private Object returnObject;
  private Object target;

  public CtMethodSecurityExpressionRoot(Authentication authentication) {

    if (authentication == null) {
      throw new IllegalArgumentException("Authentication object cannot be null");
    }

    this.authentication = authentication;
  }

  @Override
  public void setFilterObject(Object filterObject) {
    this.filterObject = filterObject;
  }

  @Override
  public Object getFilterObject() {
    return filterObject;
  }

  @Override
  public void setReturnObject(Object returnObject) {
    this.returnObject = returnObject;
  }

  @Override
  public Object getReturnObject() {
    return returnObject;
  }

  void setThis(Object target) {
    this.target = target;
  }

  @Override
  public Object getThis() {
    return target;
  }

  @Override
  public Authentication getAuthentication() {
    return this.authentication;
  }

  @Override
  public boolean hasAuthority(String authority) {
    throw new RuntimeException("method hasAuthority() not allowed");
  }

  @Override
  public boolean hasAnyAuthority(String... authorities) {
    throw new RuntimeException("method hasAuthority() not allowed");
  }

  @Override
  public boolean hasRole(String role) {
    throw new RuntimeException("method hasRole() not allowed");
  }

  @Override
  public boolean hasAnyRole(String... roles) {
    throw new RuntimeException("method hasAnyRole() not allowed");
  }

  @Override
  public boolean permitAll() {
    throw new RuntimeException("method permitAll() not allowed");
  }

  @Override
  public boolean denyAll() {
    throw new RuntimeException("method denyAll() not allowed");
  }

  @Override
  public boolean hasPermission(Object target, Object permission) {
    throw new RuntimeException("method hasPermission() not allowed");
  }

  @Override
  public boolean hasPermission(Object targetId, String targetType, Object permission) {
    throw new RuntimeException("method hasPermission() not allowed");
  }

  @Override
  public boolean isAuthenticated() {
    return this.authentication != null && this.authentication.getPrincipal() != null;
  }

  @Override
  public boolean isFullyAuthenticated() {
    return isAuthenticated();
  }

  @Override
  public boolean isAnonymous() {
    return !isAuthenticated();
  }

  @Override
  public boolean isRememberMe() {
    return false;
  }

  public final Object getPrincipal() {
    return this.authentication.getPrincipal();
  }
}
