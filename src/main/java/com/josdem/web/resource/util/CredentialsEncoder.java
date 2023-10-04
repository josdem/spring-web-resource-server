package com.josdem.web.resource.util;

import java.util.Base64;

public class CredentialsEncoder {

  public static String encode(String username, String password) {
    String credentialsFormatted = String.format("%s:%s", username, password);
    return Base64.getEncoder().encodeToString(credentialsFormatted.getBytes());
  }
}
