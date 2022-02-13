package com.czetsuyatech.web.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public interface CtJwtTokenProcessor {

  Authentication getAuthentication(HttpServletRequest request) throws BadJOSEException, ParseException, JOSEException, Exception;
}