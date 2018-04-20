package org.iakuh.skeleton.api;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodeTest {

  public static void main(String[] args) {
    System.out.println(new BCryptPasswordEncoder().encode("Hk123456"));
  }
}
