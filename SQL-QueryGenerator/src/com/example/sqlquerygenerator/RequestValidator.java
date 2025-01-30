package com.example.sqlquerygenerator;

/**
 * Клас RequestValidator надає методи для валідації вмісту запиту. Використовується для перевірки,
 * чи є вміст запиту коректним (не порожнім та не лише пробілами).
 */
public class RequestValidator {

  /**
   * Перевіряє, чи є вміст запиту дійсним. Вміст вважається дійсним, якщо він не є null і не
   * складається тільки з пробілів.
   *
   * @param requestContent вміст запиту, який потрібно перевірити
   * @return true, якщо вміст запиту не порожній і не складається тільки з пробілів; false в іншому
   * випадку
   */
  public static boolean validateRequestContent(String requestContent) {
    return requestContent != null && !requestContent.trim().isEmpty();
  }
}
