package com.example.sqlquerygenerator.unitofwork;

import com.example.sqlquerygenerator.User;
import com.example.sqlquerygenerator.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

public class UnitOfWork {

  private final UserRepository userRepository; // Репозиторій користувачів

  // Список транзакційних операцій
  private final List<Runnable> operations = new ArrayList<>();

  // Конструктор, який приймає репозиторій
  public UnitOfWork(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // Метод для отримання репозиторію користувачів
  public UserRepository getUserRepository() {
    return userRepository;
  }

  // Додати користувача в список операцій
  public void registerAddUser(User user) {
    operations.add(() -> userRepository.addUser(user));
  }

  // Оновити користувача в списку операцій
  public void registerUpdateUser(User user) {
    operations.add(() -> userRepository.updateUser(user));
  }

  // Видалити користувача за його ID
  public void registerDeleteUser(int userId) {
    operations.add(() -> userRepository.deleteUser(userId));
  }

  // Виконати всі зареєстровані операції
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

  // Скасувати всі зареєстровані операції
  public void rollback() {
    operations.clear();
    System.out.println("Транзакція скасована.");
  }
}
