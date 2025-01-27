package com.example.sqlquerygenerator.repository;

import java.util.List;

public class SQLQueryRepository {

  public String generateInsertQuery(SQLRequest request) {
    // Використовуємо String.join() для списків
    String columns = String.join(", ", request.getColumns());
    String values = String.join(", ", request.getValues());
    return "INSERT INTO " + request.getTableName() + " (" + columns + ") VALUES (" + values + ");";
  }

  public String generateSelectQuery(SQLRequest request) {
    // Перевіряємо наявність стовпців та умов
    String columns = String.join(", ", request.getColumns());
    String condition = request.getCondition() != null ? " WHERE " + request.getCondition() : "";
    return "SELECT " + columns + " FROM " + request.getTableName() + condition + ";";
  }

  public String generateUpdateQuery(SQLRequest request) {
    StringBuilder setClause = new StringBuilder();
    List<String> columns = request.getColumns();
    List<String> values = request.getValues();
    // Перевіряємо, що кількість стовпців і значень однакова
    for (int i = 0; i < columns.size(); i++) {
      setClause.append(columns.get(i)).append(" = '").append(values.get(i)).append("'");
      if (i < columns.size() - 1) {
        setClause.append(", ");
      }
    }
    return "UPDATE " + request.getTableName() + " SET " + setClause +
        (request.getCondition() != null ? " WHERE " + request.getCondition() : "") + ";";
  }

  public String generateDeleteQuery(SQLRequest request) {
    return "DELETE FROM " + request.getTableName() +
        (request.getCondition() != null ? " WHERE " + request.getCondition() : "") + ";";
  }
}
