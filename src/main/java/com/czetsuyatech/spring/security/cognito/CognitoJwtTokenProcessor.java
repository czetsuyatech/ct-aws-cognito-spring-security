package com.czetsuyatech.spring.security.cognito;

import com.czetsuyatech.spring.security.SecurityConstants;
import com.czetsuyatech.spring.security.exceptions.InvalidIdTokenException;
import com.czetsuyatech.spring.security.exceptions.InvalidJwtIssuerException;
import com.czetsuyatech.spring.security.jwt.CtJwtTokenProcessor;
import com.czetsuyatech.spring.security.jwt.CtPrincipal;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 * @since
 */
@Slf4j
@RequiredArgsConstructor
public class CognitoJwtTokenProcessor implements CtJwtTokenProcessor {

  private final ConfigurableJWTProcessor configurableJWTProcessor;
  private final CognitoJwtConfigData cognitoJwtConfigData;

  @Override
  public Authentication getAuthentication(HttpServletRequest request)
      throws Exception {

    String idToken = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
    if (StringUtils.hasText(idToken)) {

      JWTClaimsSet claimsSet = null;

      claimsSet = configurableJWTProcessor.process(stripBearerToken(idToken), null);

      if (!isIssuedCorrectly(claimsSet)) {
        throw new InvalidJwtIssuerException(
            String.format("Issuer %s in JWT token doesn't match Cognito IDP %s", claimsSet.getIssuer(),
                cognitoJwtConfigData.getIssuerId()));
      }

      if (!isIdToken(claimsSet)) {
        throw new InvalidIdTokenException("JWT Token is not a valid IdToken");
      }

      String username = claimsSet.getClaims().get(cognitoJwtConfigData.getUserNameField()).toString();

      if (username != null) {
        List<String> groups = (List<String>) claimsSet.getClaims().get(cognitoJwtConfigData.getGroupField());
        List<GrantedAuthority> grantedAuthorities = convertList(groups,
            group -> new SimpleGrantedAuthority(SecurityConstants.ROLE_PREFIX + group.toUpperCase()));
        User user = new User(username, SecurityConstants.EMPTY_PWD, grantedAuthorities);

        CtPrincipal principal = new CtPrincipal(username, stripBearerToken(idToken), user);
        return new CognitoAuthenticationToken(principal, claimsSet, grantedAuthorities);
      }
    }

    log.trace("No idToken found in HTTP Header");
    return null;
  }

  private String stripBearerToken(String token) {
    return token.startsWith(SecurityConstants.BEARER_PREFIX) ? token.substring(SecurityConstants.BEARER_PREFIX.length())
        : token;
  }

  private boolean isIssuedCorrectly(JWTClaimsSet claimsSet) {
    return claimsSet.getIssuer().equals(cognitoJwtConfigData.getIssuerId());
  }

  private boolean isIdToken(JWTClaimsSet claimsSet) {
    Object tokenUse = claimsSet.getClaim("token_use");
    return tokenUse.equals("id") || tokenUse.equals("access");
  }

  private static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
    return from.stream().map(func).collect(Collectors.toList());
  }
}
