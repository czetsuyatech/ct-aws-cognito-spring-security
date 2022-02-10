package com.czetsuyatech.spring.security.cognito;

import com.czetsuyatech.spring.security.jwt.CtPrincipal;
import com.nimbusds.jwt.JWTClaimsSet;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public class CognitoAuthenticationToken extends AbstractAuthenticationToken implements Authentication {

  private final CtPrincipal principal;
  private JWTClaimsSet jwtClaimsSet;
  private String token;
  private User user;

  public CognitoAuthenticationToken(CtPrincipal principal, String token, User user, JWTClaimsSet jwtClaimsSet,
      Collection<? extends GrantedAuthority> authorities) {

    super(authorities);
    this.principal = principal;
    this.token = token;
    this.user = user;
    this.jwtClaimsSet = jwtClaimsSet;
    super.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  @Override
  public Object getDetails() {
    return user;
  }

  public JWTClaimsSet getJwtClaimsSet() {
    return jwtClaimsSet;
  }
}
