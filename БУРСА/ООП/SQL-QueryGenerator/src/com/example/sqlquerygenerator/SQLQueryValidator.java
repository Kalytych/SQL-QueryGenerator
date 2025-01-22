package com.example.sqlquerygenerator;

public class SQLQueryValidator {

  public static boolean validateSQLQuery(String query) {
    // Тут можна додати більш складну перевірку на SQL синтаксис, наприклад:
    return query != null && query.trim().toUpperCase().startsWith("SELECT");
  }
}
