package com.example.sqlquerygenerator.unitofwork;

import com.example.sqlquerygenerator.User;
import com.example.sqlquerygenerator.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас UnitOfWork реалізує патерн "Unit of Work", який дозволяє обробляти транзакції з
 * користувачами (додавання, оновлення, видалення) в рамках єдиного процесу. Він забезпечує
 * реєстрацію операцій і їх виконання або скасування.
 */
public class UnitOfWork {

  private final UserRepository userRepository; // Репозиторій користувачів

  // Список транзакційних операцій
  private final List<Runnable> operations = new ArrayList<>();

  /**
   * Конструктор класу, який ініціалізує репозиторій користувачів.
   *
   * @param userRepository репозиторій для виконання операцій з користувачами
   */
  public UnitOfWork(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Метод для отримання репозиторію користувачів.
   *
   * @return репозиторій користувачів
   */
  public UserRepository getUserRepository() {
    return userRepository;
  }

  /**
   * Додає операцію додавання нового користувача до списку операцій.
   *
   * @param user користувач, якого потрібно додати
   */
  public void registerAddUser(User user) {
    operations.add(() -> userRepository.addUser(user));
  }

  /**
   * Додає операцію оновлення користувача до списку операцій.
   *
   * @param user користувач, якого потрібно оновити
   */
  public void registerUpdateUser(User user) {
    operations.add(() -> userRepository.updateUser(user));
  }

  /**
   * Додає операцію видалення користувача за його ID до списку операцій.
   *
   * @param userId ID користувача, якого потрібно видалити
   */
  public void registerDeleteUser(int userId) {
    operations.add(() -> userRepository.deleteUser(userId));
  }

  /**
   * Виконує всі зареєстровані операції. Якщо одна з операцій не вдалася, всі операції
   * скасовуються.
   */
  public void commit() {
    try {
      for (Runnable operation : operations) {
        operation.run();
      }
      operations.clear(); // Очистити список після виконання
      System.out.println("Транзакція успішно виконана!");
    } catch (Exception e) {
      System.out.println("Помилка під час виконання транзакції: " + e.getMessage());
      rollback(); // Скасувати операції в разі помилки
    }
  }

  /**
   * Скасовує всі зареєстровані операції. Це використовується в разі помилки при виконанні
   * транзакції.
   */
  public void rollback() {
    operations.clear();
    System.out.println("Транзакція скасована.");
  }
}
