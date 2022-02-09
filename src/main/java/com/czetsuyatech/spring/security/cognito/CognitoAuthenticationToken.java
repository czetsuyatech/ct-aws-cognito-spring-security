package com.czetsuyatech.spring.security.cognito;

import com.czetsuyatech.spring.security.jwt.CtPrincipal;
import com.nimbusds.jwt.JWTClaimsSet;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public class CognitoAuthenticationToken extends AbstractAuthenticationToken implements Authentication {

  private final CtPrincipal principal;
  private JWTClaimsSet jwtClaimsSet;

  public CognitoAuthenticationToken(CtPrincipal principal, JWTClaimsSet jwtClaimsSet,
      Collection<? extends GrantedAuthority> authorities) {

    super(authorities);
    this.principal = principal;
    this.jwtClaimsSet = jwtClaimsSet;
    super.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  public JWTClaimsSet getJwtClaimsSet() {
    return jwtClaimsSet;
  }
}
