package com.czetsuyatech.security;

import com.czetsuyatech.spring.security.CtHttpSecurityConfigurer;
import com.czetsuyatech.spring.security.EnableCtSecurity;
import com.czetsuyatech.spring.security.method.CtMethodSecurityExpressionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Configuration
@EnableCtSecurity
@RequiredArgsConstructor
public class CtAppSecurityConfig {

  @Bean
  public CtHttpSecurityConfigurer httpSecurityConfig() {
    return http ->
        http.csrf().disable()
            .cors()

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .sessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy())

            .and()
            .httpBasic().disable()
            .formLogin().disable()

            .authorizeHttpRequests()
            .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
            .antMatchers("/api/**").authenticated()
            .anyRequest().permitAll()
        ;
  }

  @Bean
  public CtMethodSecurityExpressionHandler methodSecurityExpressionHandler() {
    return CtAppMethodSecurityExpressionExtension::new;
  }
}