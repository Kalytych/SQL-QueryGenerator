package com.example.sqlquerygenerator.repository;

import com.example.sqlquerygenerator.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

  private final List<User> users = new ArrayList<>();

  // Створити користувача
  public void addUser(User user) {
    users.add(user);
    System.out.println("Користувача додано: " + user);
  }

  // Отримати всіх користувачів
  public List<User> getAllUsers() {
    return new ArrayList<>(users);
  }

  // Знайти користувача за ID
  public Optional<User> findUserById(int id) {
    return users.stream().filter(user -> user.getId() == id).findFirst();
  }

  // Оновити дані користувача
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
    return false;
  }

  // Видалити користувача
  public boolean deleteUser(int id) {
    return users.removeIf(user -> user.getId() == id);
  }
}
