package com.example.sqlquerygenerator.Request.java;

public class Request {

  private final String tableName;
  private final String[] columns;
  private final String[] values;
  private String condition;

  /**
   * Конструктор для INSERT-запитів.
   *
   * @param tableName назва таблиці
   * @param columns   масив стовпців
   * @param values    масив значень
   */
  public Request(String tableName, String[] columns, String[] values) {
    if (columns.length != values.length) {
      throw new IllegalArgumentException("Кількість стовпців і значень має співпадати.");
    }
    this.tableName = tableName;
    this.columns = columns;
    this.values = values;
  }

  /**
   * Конструктор для SELECT/UPDATE/DELETE-запитів.
   *
   * @param tableName назва таблиці
   * @param columns   масив стовпців
   * @param values    масив значень
   * @param condition умова запиту
   */
  public Request(String tableName, String[] columns, String[] values, String condition) {
    this(tableName, columns, values); // Виклик конструктора з трьома параметрами
    this.condition = condition;
  }

  // Геттери
  public String getTableName() {
    return tableName;
  }

  public String[] getColumns() {
    return columns;
  }

  public String[] getValues() {
    return values;
  }

  public String getCondition() {
    return condition;
  }

  /**
   * Встановлення нової умови запиту.
   *
   * @param condition нова умова
   */
  public void setCondition(String condition) {
    this.condition = condition;
  }
}
