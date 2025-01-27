package com.example.sqlquerygenerator.repository;

import java.util.List;
import java.util.stream.Collectors;

public class SQLRequest {

  private final String tableName;
  private final List<String> columns;
  private final List<String> values;
  private final String condition;

  // Конструктор
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

  // Метод для генерації INSERT-запиту
  public String generateInsertQuery() {
    if (columns == null || values == null || columns.size() != values.size()) {
      throw new IllegalArgumentException("Columns and values must be the same size.");
    }

    String columnsStr = String.join(", ", columns);
    String valuesStr = values.stream()
        .map(value -> "'" + value + "'")
        .collect(Collectors.joining(", "));

    return "INSERT INTO " + tableName + " (" + columnsStr + ") VALUES (" + valuesStr + ");";
  }

  // Метод для генерації SELECT-запиту
  public String generateSelectQuery() {
    String columnsStr = columns != null ? String.join(", ", columns) : "*";
    String conditionStr = (condition != null && !condition.isEmpty()) ? " WHERE " + condition : "";
    return "SELECT " + columnsStr + " FROM " + tableName + conditionStr + ";";
  }

  // Метод для генерації UPDATE-запиту
  public String generateUpdateQuery() {
    if (columns == null || values == null || columns.size() != values.size()) {
      throw new IllegalArgumentException("Columns and values must be the same size.");
    }

    StringBuilder setClause = new StringBuilder();
    for (int i = 0; i < columns.size(); i++) {
      setClause.append(columns.get(i)).append(" = '").append(values.get(i)).append("'");
      if (i < columns.size() - 1) {
        setClause.append(", ");
      }
    }

    String conditionStr = (condition != null && !condition.isEmpty()) ? " WHERE " + condition : "";

    return "UPDATE " + tableName + " SET " + setClause + conditionStr + ";";
  }

  // Метод для генерації DELETE-запиту
  public String generateDeleteQuery() {
    String conditionStr = (condition != null && !condition.isEmpty()) ? " WHERE " + condition : "";
    return "DELETE FROM " + tableName + conditionStr + ";";
  }
}
