package com.czetsuyatech.web.security.identity;

import java.security.Principal;
import java.util.Set;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public interface CtAccount {

  Principal getPrincipal();

  Set<String> getRoles();
}
