package com.example.sqlquerygenerator.repository;

import com.example.sqlquerygenerator.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

  private final List<User> users = new ArrayList<>();

  /**
   * Додає нового користувача до списку. Перед додаванням перевіряє, чи немає дублювання ID.
   *
   * @param user новий користувач
   */
  public void addUser(User user) {
    if (findUserById(user.getId()).isPresent()) {
      System.out.println("Користувач з таким ID вже існує: " + user.getId());
    } else {
      users.add(user);
      System.out.println("Користувача додано: " + user);
    }
  }

  /**
   * Повертає список усіх користувачів.
   *
   * @return список користувачів
   */
  public List<User> getAllUsers() {
    return new ArrayList<>(users);
  }

  /**
   * Повертає користувача за його ID.
   *
   * @param id унікальний ідентифікатор користувача
   * @return Optional об'єкт користувача
   */
  public Optional<User> findUserById(int id) {
    return users.stream().filter(user -> user.getId() == id).findFirst();
  }

  /**
   * Оновлює дані користувача.
   *
   * @param updatedUser об'єкт із новими даними користувача
   * @return true, якщо користувача успішно оновлено, інакше false
   */
  public boolean updateUser(User updatedUser) {
    Optional<User> userOptional = findUserById(updatedUser.getId());
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      user.setUsername(updatedUser.getUsername());
      user.setPassword(updatedUser.getPassword());
      user.setAge(updatedUser.getAge());
      System.out.println("Користувача оновлено: " + user);
      return true;
    }
    System.out.println("Користувача з ID " + updatedUser.getId() + " не знайдено.");
    return false;
  }

  /**
   * Видаляє користувача за ID.
   *
   * @param id унікальний ідентифікатор користувача
   * @return true, якщо користувача успішно видалено, інакше false
   */
  public boolean deleteUser(int id) {
    boolean removed = users.removeIf(user -> user.getId() == id);
    if (removed) {
      System.out.println("Користувача з ID " + id + " видалено.");
    } else {
      System.out.println("Користувача з ID " + id + " не знайдено.");
    }
    return removed;
  }
}
