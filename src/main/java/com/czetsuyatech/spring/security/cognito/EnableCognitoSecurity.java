package com.czetsuyatech.spring.security.cognito;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {
    CognitoAutoConfiguration.class
})
public @interface EnableCognitoSecurity {

}
