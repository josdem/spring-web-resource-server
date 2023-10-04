package com.josdem.web.resource.util;

public class CredentialsEncoder {

  public static String encode(String username, String password) {
    String credentialsFormatted = String.format("%s:%s", username, password);
    return "Basic " + java.util.Base64.getEncoder().encodeToString(credentialsFormatted.getBytes());
  }
}
