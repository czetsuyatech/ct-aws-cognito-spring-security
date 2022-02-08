package com.czetsuyatech.security;

import com.czetsuyatech.spring.security.CtHttpSecurityConfigurer;
import com.czetsuyatech.spring.security.CtMethodSecurityExpressionHandler;
import com.czetsuyatech.spring.security.EnableCtSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;

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

            .and()
//          .httpBasic().disable()
            .formLogin().disable()

            .authorizeHttpRequests()
            .antMatchers("/api/**").authenticated()
            .and()
            .oauth2Login()

            .and()
            .authorizeHttpRequests()
            .antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
            .anyRequest().permitAll()
        ;
  }

  @Bean
  public CtMethodSecurityExpressionHandler methodSecurityExpressionHandler() {
    return CtAppMethodSecurityExpressionExtension::new;
  }
}