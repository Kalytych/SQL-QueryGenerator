package com.example.sqlquerygenerator;


public class UserValidator {

  // Метод для валідації даних користувача під час реєстрації
  public static boolean validateUser(User user) {
    if (user.getName() == null || user.getName().isEmpty()) {
      System.out.println("Ім'я не може бути порожнім.");
      return false;
    }

    if (user.getAge() <= 0) {
      System.out.println("Вік має бути більшим за 0.");
      return false;
    }

    if (user.getUsername() == null || user.getUsername().isEmpty()) {
      System.out.println("Логін не може бути порожнім.");
      return false;
    }

    if (user.getPassword() == null || user.getPassword().length() < 6) {
      System.out.println("Пароль має містити щонайменше 6 символів.");
      return false;
    }

    return true;
  }
}

