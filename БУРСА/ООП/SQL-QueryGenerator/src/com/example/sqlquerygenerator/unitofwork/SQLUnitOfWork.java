package com.example.sqlquerygenerator.unitofwork;


import com.example.sqlquerygenerator.repository.SQLQueryRepository;
import com.example.sqlquerygenerator.repository.SQLRequest;
import java.util.ArrayList;
import java.util.List;

public class SQLUnitOfWork {

  private final SQLQueryRepository sqlQueryRepository;
  private final List<String> queries = new ArrayList<>();

  public SQLUnitOfWork(SQLQueryRepository sqlQueryRepository) {
    this.sqlQueryRepository = sqlQueryRepository;
  }

  public void registerInsert(SQLRequest request) {
    queries.add(sqlQueryRepository.generateInsertQuery(request));
  }

  public void registerSelect(SQLRequest request) {
    queries.add(sqlQueryRepository.generateSelectQuery(request));
  }

  public void registerUpdate(SQLRequest request) {
    queries.add(sqlQueryRepository.generateUpdateQuery(request));
  }

  public void registerDelete(SQLRequest request) {
    queries.add(sqlQueryRepository.generateDeleteQuery(request));
  }

  public void commit() {
    System.out.println("Executing SQL Queries:");
    for (String query : queries) {
      System.out.println(query);
    }
    queries.clear();
  }

  public void rollback() {
    queries.clear();
    System.out.println("All SQL queries have been rolled back.");
  }
}
