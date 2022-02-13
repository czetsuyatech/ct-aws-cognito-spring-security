package com.czetsuyatech.web.security.jwt;

import com.czetsuyatech.web.security.identity.CtAccount;
import com.czetsuyatech.web.security.identity.OidcCtAccount;
import java.security.Principal;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public class CtAuthenticationToken extends AbstractAuthenticationToken implements Authentication {

  private final Principal principal;

  public CtAuthenticationToken(CtAccount account) {

    super((Collection) null);
    Assert.notNull(account, "CtAccount cannot be null");
    Assert.notNull(account.getPrincipal(), "CtAccount.getPrincipal() cannot be null");

    this.principal = account.getPrincipal();
    setDetails(account);
    super.setAuthenticated(true);
  }

  public CtAuthenticationToken(CtAccount account, Collection<? extends GrantedAuthority> authorities) {

    super(authorities);
    Assert.notNull(account, "CtAccount cannot be null");
    Assert.notNull(account.getPrincipal(), "CtAccount.getPrincipal() cannot be null");

    this.principal = account.getPrincipal();
    setDetails(account);
    super.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return getAccount().getCtSecurityContext();
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  public OidcCtAccount getAccount() {
    return (OidcCtAccount) this.getDetails();
  }
}
