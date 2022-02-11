package com.czetsuyatech.spring.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Component
@ConfigurationProperties(prefix = "app.security.jwt.cognito")
@Data
public class CognitoJwtConfigData {

  private static final String COGNITO_IDENTITY_POOL_URL = "https://cognito-idp.%s.amazonaws.com/%s";
  private static final String JSON_WEB_TOKEN_SET_URL_SUFFIX = "/.well-known/jwks.json";

  private String poolId;
  private String region;
  private String groupField;
  private String userNameField;

  private int connectionTimeout = 2000;
  private int readTimeout = 2000;

  private String jwkUrl;
  private String issuerId;

  public String getJwkUrl() {

    if (jwkUrl == null || jwkUrl.isEmpty()) {
      return String.format(COGNITO_IDENTITY_POOL_URL + JSON_WEB_TOKEN_SET_URL_SUFFIX, region, poolId);
    }

    return jwkUrl;
  }

  public String getIssuerId() {

    if (issuerId == null || issuerId.isEmpty()) {
      return String.format(COGNITO_IDENTITY_POOL_URL, region, poolId);
    }

    return issuerId;
  }
}