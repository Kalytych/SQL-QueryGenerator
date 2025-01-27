package com.example.sqlquerygenerator.Request.java;

public class Request {

  private final String tableName;
  private final String[] columns;
  private final String[] values;
  private String condition;

  // Конструктор для INSERT-запитів
  public Request(String tableName, String[] columns, String[] values) {
    this.tableName = tableName;
    this.columns = columns;
    this.values = values;
  }

  // Конструктор для SELECT/UPDATE/DELETE-запитів
  public Request(String tableName, String[] columns, String[] values, String condition) {
    this.tableName = tableName;
    this.columns = columns;
    this.values = values;
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
}
