package com.czetsuyatech.web.security;

import com.czetsuyatech.web.security.identity.CtRole;
import com.czetsuyatech.web.security.jwt.CtAuthenticationToken;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.stereotype.Component;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Component
public class CtAuthenticationProvider implements AuthenticationProvider {

  private GrantedAuthoritiesMapper grantedAuthoritiesMapper;

  public void setGrantedAuthoritiesMapper(GrantedAuthoritiesMapper grantedAuthoritiesMapper) {
    this.grantedAuthoritiesMapper = grantedAuthoritiesMapper;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    CtAuthenticationToken token = (CtAuthenticationToken) authentication;
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    token.getAccount().getRoles().forEach(e -> {
      grantedAuthorities.add(new CtRole(e));
    });

    return new CtAuthenticationToken(token.getAccount(), mapAuthorities(grantedAuthorities));
  }

  private Collection<? extends GrantedAuthority> mapAuthorities(List<GrantedAuthority> authorities) {
    return this.grantedAuthoritiesMapper != null ? this.grantedAuthoritiesMapper.mapAuthorities(authorities) :
        authorities;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return CtAuthenticationToken.class.isAssignableFrom(authentication);
  }
}