package com.example.sqlquerygenerator.repository;

import java.util.List;

/**
 * Репозиторій для генерації SQL-запитів. Містить методи для створення INSERT, SELECT, UPDATE та
 * DELETE запитів.
 */
public class SQLQueryRepository {

  /**
   * Генерує INSERT-запит на основі наданого SQLRequest.
   *
   * @param request об'єкт SQLRequest, що містить інформацію про таблицю, стовпці та значення
   * @return SQL-запит у форматі INSERT
   */
  public String generateInsertQuery(SQLRequest request) {
    String columns = String.join(", ", request.getColumns());
    String values = String.join(", ", request.getValues());
    return "INSERT INTO " + request.getTableName() + " (" + columns + ") VALUES (" + values + ");";
  }

  /**
   * Генерує SELECT-запит на основі наданого SQLRequest.
   *
   * @param request об'єкт SQLRequest, що містить інформацію про таблицю, стовпці та умову
   * @return SQL-запит у форматі SELECT
   */
  public String generateSelectQuery(SQLRequest request) {
    String columns = String.join(", ", request.getColumns());
    String condition = request.getCondition() != null ? " WHERE " + request.getCondition() : "";
    return "SELECT " + columns + " FROM " + request.getTableName() + condition + ";";
  }

  /**
   * Генерує UPDATE-запит на основі наданого SQLRequest.
   *
   * @param request об'єкт SQLRequest, що містить інформацію про таблицю, стовпці, значення та
   *                умову
   * @return SQL-запит у форматі UPDATE
   */
  public String generateUpdateQuery(SQLRequest request) {
    StringBuilder setClause = new StringBuilder();
    List<String> columns = request.getColumns();
    List<String> values = request.getValues();

    for (int i = 0; i < columns.size(); i++) {
      setClause.append(columns.get(i)).append(" = '").append(values.get(i)).append("'");
      if (i < columns.size() - 1) {
        setClause.append(", ");
      }
    }
    return "UPDATE " + request.getTableName() + " SET " + setClause +
        (request.getCondition() != null ? " WHERE " + request.getCondition() : "") + ";";
  }

  /**
   * Генерує DELETE-запит на основі наданого SQLRequest.
   *
   * @param request об'єкт SQLRequest, що містить інформацію про таблицю та умову
   * @return SQL-запит у форматі DELETE
   */
  public String generateDeleteQuery(SQLRequest request) {
    return "DELETE FROM " + request.getTableName() +
        (request.getCondition() != null ? " WHERE " + request.getCondition() : "") + ";";
  }
}
