package com.example.sqlquerygenerator.repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Клас SQLRequest відповідає за зберігання інформації для генерації SQL-запитів та надає методи для
 * створення INSERT, SELECT, UPDATE і DELETE запитів.
 */
public class SQLRequest {

  private final String tableName; // Назва таблиці
  private final List<String> columns; // Список назв стовпців
  private final List<String> values; // Список значень для відповідних стовпців
  private final String condition; // Умова для WHERE

  /**
   * Конструктор для створення об'єкта SQLRequest.
   *
   * @param tableName назва таблиці
   * @param columns   список назв стовпців
   * @param values    список значень для відповідних стовпців
   * @param condition умова WHERE (може бути null)
   */
  public SQLRequest(String tableName, List<String> columns, List<String> values, String condition) {
    this.tableName = tableName;
    this.columns = columns;
    this.values = values;
    this.condition = condition;
  }

  public String getTableName() {
    return tableName;
  }

  public List<String> getColumns() {
    return columns;
  }

  public List<String> getValues() {
    return values;
  }

  public String getCondition() {
    return condition;
  }

  /**
   * Генерує SQL-запит для операції INSERT.
   *
   * @return строка SQL-запиту INSERT
   * @throws IllegalArgumentException якщо розмір списків columns та values не співпадає
   */
  public String generateInsertQuery() {
    if (columns == null || values == null || columns.size() != values.size()) {
      throw new IllegalArgumentException("Columns and values must be the same size.");
    }

    String columnsStr = String.join(", ", columns);
    String valuesStr = values.stream()
        .map(value -> value.contains("'") ? "'" + value.replace("'", "''") + "'" : "'" + value + "'")
        .collect(Collectors.joining(", "));

    return "INSERT INTO " + tableName + " (" + columnsStr + ") VALUES (" + valuesStr + ");";
  }

  /**
   * Генерує SQL-запит для операції SELECT.
   *
   * @return строка SQL-запиту SELECT
   * @throws IllegalArgumentException якщо умова не є правильною
   */
  public String generateSelectQuery() {
    if (condition != null && !condition.isEmpty() && !isValidCondition(condition)) {
      throw new IllegalArgumentException("Неправильна умова.");
    }

    String columnsStr = (columns != null && !columns.isEmpty()) ? String.join(", ", columns) : "*";
    String conditionStr = (condition != null && !condition.isEmpty()) ? " WHERE " + condition : "";
    return "SELECT " + columnsStr + " FROM " + tableName + conditionStr + ";";
  }

  /**
   * Генерує SQL-запит для операції UPDATE.
   *
   * @return строка SQL-запиту UPDATE
   * @throws IllegalArgumentException якщо розмір списків columns та values не співпадає
   * @throws IllegalArgumentException якщо умова не є правильною
   */
  public String generateUpdateQuery() {
    if (columns == null || values == null || columns.size() != values.size()) {
      throw new IllegalArgumentException("Columns and values must be the same size.");
    }

    if (condition != null && !condition.isEmpty() && !isValidCondition(condition)) {
      throw new IllegalArgumentException("Неправильна умова.");
    }

    StringBuilder setClause = new StringBuilder();
    for (int i = 0; i < columns.size(); i++) {
      setClause.append(columns.get(i)).append(" = '")
          .append(values.get(i).contains("'") ? values.get(i).replace("'", "''") : values.get(i))
          .append("'");

      if (i < columns.size() - 1) {
        setClause.append(", ");
      }
    }

    String conditionStr = (condition != null && !condition.isEmpty()) ? " WHERE " + condition : "";

    return "UPDATE " + tableName + " SET " + setClause + conditionStr + ";";
  }

  /**
   * Генерує SQL-запит для операції DELETE.
   *
   * @return строка SQL-запиту DELETE
   * @throws IllegalArgumentException якщо умова не є правильною
   */
  public String generateDeleteQuery() {
    if (condition != null && !condition.isEmpty() && !isValidCondition(condition)) {
      throw new IllegalArgumentException("Неправильна умова.");
    }

    String conditionStr = (condition != null && !condition.isEmpty()) ? " WHERE " + condition : "";
    return "DELETE FROM " + tableName + conditionStr + ";";
  }

  /**
   * Перевірка коректності умови.
   *
   * @param condition умова
   * @return true, якщо умова правильна, інакше false
   */
  private boolean isValidCondition(String condition) {
    // Простий регулярний вираз для перевірки умови
    // Можна змінити на складніший, залежно від вашої бізнес-логіки
    return condition.matches("^[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*.*");
  }
}
