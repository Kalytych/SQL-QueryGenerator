package com.example.sqlquerygenerator;

public class RequestValidator {

  public static boolean validateRequestContent(String requestContent) {
    return requestContent != null && !requestContent.trim().isEmpty();
  }
}
