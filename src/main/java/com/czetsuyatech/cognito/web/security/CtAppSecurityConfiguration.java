package com.czetsuyatech.cognito.web.security;

import com.czetsuyatech.spring.security.EnableCtSecurity;
import com.czetsuyatech.spring.security.method.CtMethodSecurityExpressionHandlerFactory;
import com.czetsuyatech.spring.security.web.CtHttpSecurityConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Configuration
@RequiredArgsConstructor
@EnableCtSecurity
public class CtAppSecurityConfiguration {

  @Bean
  public CtHttpSecurityConfigurer httpSecurityConfig() {

    return http ->
        http
            .authorizeHttpRequests(authz -> authz
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            );
  }

  @Bean
  public CtMethodSecurityExpressionHandlerFactory methodSecurityFactory() {
    return CtAppMethodSecurityExpressionRoot::new;
  }
}