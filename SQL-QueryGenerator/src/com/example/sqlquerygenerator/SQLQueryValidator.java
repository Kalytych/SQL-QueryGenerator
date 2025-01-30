package com.example.sqlquerygenerator;

/**
 * Клас SQLQueryValidator надає методи для валідації SQL запитів. Використовується для перевірки, чи
 * є запит коректним на базовому рівні, зокрема, чи починається він з ключового слова SELECT.
 */
public class SQLQueryValidator {

  /**
   * Перевіряє, чи є SQL запит дійсним. В даному випадку перевіряється, чи є запит непорожнім і чи
   * починається він з ключового слова "SELECT". Можна додавати більш складні перевірки SQL
   * синтаксису в цю функцію.
   *
   * @param query SQL запит, який потрібно перевірити
   * @return true, якщо запит не порожній і починається з "SELECT"; false в іншому випадку
   */
  public static boolean validateSQLQuery(String query) {
    // Тут можна додати більш складну перевірку на SQL синтаксис, наприклад:
    return query != null && query.trim().toUpperCase().startsWith("SELECT");
  }
}
