package com.czetsuyatech.spring.security.cognito;

import static com.nimbusds.jose.JWSAlgorithm.RS256;

import com.czetsuyatech.spring.security.jwt.CtJwtAuthenticationProvider;
import com.czetsuyatech.spring.security.jwt.CtJwtTokenProcessor;
import com.czetsuyatech.spring.security.jwt.CtPrincipal;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AuthenticationProvider;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass({CognitoJwtAuthenticationFilter.class, CognitoJwtTokenProcessor.class})
public class CognitoAutoConfiguration {

  private final CognitoJwtConfigData cognitoJwtConfigData;

  @Bean
  @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
  public CtPrincipal jwtPrincipal() {
    return new CtPrincipal();
  }

  @Bean
  public ConfigurableJWTProcessor configurableJWTProcessor() throws MalformedURLException {

    ResourceRetriever resourceRetriever = new DefaultResourceRetriever(cognitoJwtConfigData.getConnectionTimeout(),
        cognitoJwtConfigData.getReadTimeout());
    URL jwkSetURL = new URL(cognitoJwtConfigData.getJwkUrl());
    JWKSource keySource = new RemoteJWKSet(jwkSetURL, resourceRetriever);
    ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();
    JWSKeySelector keySelector = new JWSVerificationKeySelector(RS256, keySource);
    jwtProcessor.setJWSKeySelector(keySelector);

    return jwtProcessor;
  }

  @Bean
  public CtJwtTokenProcessor cognitoJwtTokenProcessor() throws MalformedURLException {
    return new CognitoJwtTokenProcessor(configurableJWTProcessor(), cognitoJwtConfigData);
  }

  @Bean
  public CognitoJwtAuthenticationFilter awsCognitoJwtAuthenticationFilter() throws MalformedURLException {
    return new CognitoJwtAuthenticationFilter(cognitoJwtTokenProcessor());
  }

  @Bean
  public AuthenticationProvider jwtAuthenticationProvider() {
    return new CtJwtAuthenticationProvider();
  }
}
