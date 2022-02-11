package com.czetsuyatech.spring.security.jwt;

import com.czetsuyatech.spring.security.SecurityConstants;
import com.czetsuyatech.spring.security.config.CognitoJwtConfigData;
import com.czetsuyatech.spring.security.exceptions.InvalidIdTokenException;
import com.czetsuyatech.spring.security.exceptions.InvalidJwtIssuerException;
import com.czetsuyatech.spring.security.identity.SimpleCtAccount;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
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
  public Authentication getAuthentication(HttpServletRequest request) throws Exception {

    String idToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(idToken)) {

      JWTClaimsSet claimsSet = null;

      claimsSet = configurableJWTProcessor.process(stripBearerToken(idToken), null);

      if (!isIssuedCorrectly(claimsSet)) {
        throw new InvalidJwtIssuerException(
            String.format("Issuer %s in JWT token doesn't match Cognito IDP %s", claimsSet.getIssuer(),
                cognitoJwtConfigData.getIssuerId()));
      }

      if (!isIdToken(claimsSet)) {
        throw new InvalidIdTokenException("JWT Token is not a valid Access or Id Token");
      }

      String username = claimsSet.getClaims().get(cognitoJwtConfigData.getUserNameField()).toString();

      if (username != null) {
        Optional<Object> optGroup = Optional.ofNullable(
            claimsSet.getClaims().get(cognitoJwtConfigData.getGroupField()));
        List<String> groups = (List<String>) optGroup.orElse(new HashSet());

        CtSecurityContext securityContext = new CtSecurityContext(stripBearerToken(idToken), null);
        CtPrincipal ctPrincipal = new CtPrincipal(username, securityContext);
        SimpleCtAccount account = new SimpleCtAccount(ctPrincipal, Set.copyOf(groups), securityContext);

        return new CtAuthenticationToken(account);
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

  private static <T, U> Set<U> convertCollection(Collection<T> from, Function<T, U> func) {
    return from.stream().map(func).collect(Collectors.toSet());
  }
}
