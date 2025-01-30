package com.example.sqlquerygenerator.unitofwork;

import com.example.sqlquerygenerator.repository.SQLQueryRepository;
import com.example.sqlquerygenerator.repository.SQLRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас SQLUnitOfWork реалізує патерн "Unit of Work" для реєстрації та виконання SQL запитів. Він
 * надає методи для реєстрації різних типів SQL запитів (INSERT, SELECT, UPDATE, DELETE) та їх
 * виконання або скасування.
 */
public class SQLUnitOfWork {

  private final SQLQueryRepository sqlQueryRepository;
  private final List<String> queries = new ArrayList<>();

  /**
   * Конструктор класу SQLUnitOfWork, який ініціалізує репозиторій для генерації SQL запитів.
   *
   * @param sqlQueryRepository репозиторій, відповідальний за генерацію SQL запитів
   */
  public SQLUnitOfWork(SQLQueryRepository sqlQueryRepository) {
    this.sqlQueryRepository = sqlQueryRepository;
  }

  /**
   * Реєструє SQL запит типу INSERT для виконання.
   *
   * @param request об'єкт SQLRequest, що містить дані для запиту INSERT
   */
  public void registerInsert(SQLRequest request) {
    queries.add(sqlQueryRepository.generateInsertQuery(request));
  }

  /**
   * Реєструє SQL запит типу SELECT для виконання.
   *
   * @param request об'єкт SQLRequest, що містить дані для запиту SELECT
   */
  public void registerSelect(SQLRequest request) {
    queries.add(sqlQueryRepository.generateSelectQuery(request));
  }

  /**
   * Реєструє SQL запит типу UPDATE для виконання.
   *
   * @param request об'єкт SQLRequest, що містить дані для запиту UPDATE
   */
  public void registerUpdate(SQLRequest request) {
    queries.add(sqlQueryRepository.generateUpdateQuery(request));
  }

  /**
   * Реєструє SQL запит типу DELETE для виконання.
   *
   * @param request об'єкт SQLRequest, що містить дані для запиту DELETE
   */
  public void registerDelete(SQLRequest request) {
    queries.add(sqlQueryRepository.generateDeleteQuery(request));
  }

  /**
   * Виконує всі зареєстровані SQL запити. Цей метод виводить кожен запит та очищає список
   * зареєстрованих запитів.
   */
  public void commit() {
    System.out.println("Виконання SQL запитів:");
    for (String query : queries) {
      System.out.println(query);
    }
    queries.clear();
  }

  /**
   * Скасовує всі зареєстровані SQL запити, очищаючи список запитів. Цей метод також виводить
   * повідомлення про скасування.
   */
  public void rollback() {
    queries.clear();
    System.out.println("Усі SQL запити були скасовані.");
  }
}
