package com.czetsuyatech.spring.security;

import static com.nimbusds.jose.JWSAlgorithm.RS256;

import com.czetsuyatech.spring.security.config.CognitoJwtConfigData;
import com.czetsuyatech.spring.security.jwt.CognitoJwtTokenProcessor;
import com.czetsuyatech.spring.security.jwt.CtJwtTokenProcessor;
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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 * @since
 */
@Configuration
@ConditionalOnClass({CtJwtTokenProcessor.class})
@EnableConfigurationProperties({CognitoJwtConfigData.class})
@RequiredArgsConstructor
public class CtSecurityAutoConfiguration {

  private final CognitoJwtConfigData cognitoJwtConfigData;

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
  public CtJwtTokenProcessor ctJwtTokenProcessor() throws MalformedURLException {
    return new CognitoJwtTokenProcessor(configurableJWTProcessor(), cognitoJwtConfigData);
  }
}
