package com.example.sqlquerygenerator;

import com.example.sqlquerygenerator.repository.UserRepository;
import com.example.sqlquerygenerator.unitofwork.UnitOfWork;
import java.util.Optional;
import java.util.Scanner;

public class Main {

  private static final UnitOfWork unitOfWork = new UnitOfWork(
      new UserRepository()); // Ініціалізація UnitOfWork з UserRepository

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("Оберіть дію:");
      System.out.println("1. Реєстрація");
      System.out.println("2. Авторизація");
      System.out.println("3. Вихід");
      int choice = scanner.nextInt();
      scanner.nextLine(); // Очистка буфера

      switch (choice) {
        case 1 -> registerUser(scanner);
        case 2 -> authorizeUser(scanner);
        case 3 -> {
          System.out.println("До побачення!");
          return;
        }
        default -> System.out.println("Неправильний вибір. Спробуйте знову.");
      }
    }
  }

  // Метод для реєстрації нового користувача через UnitOfWork
  private static void registerUser(Scanner scanner) {
    System.out.println("Реєстрація:");
    System.out.print("Введіть ім'я: ");
    String name = scanner.nextLine();

    System.out.print("Введіть вік: ");
    int age = scanner.nextInt();
    scanner.nextLine(); // Очистка буфера

    System.out.print("Введіть логін: ");
    String username = scanner.nextLine();

    System.out.print("Введіть пароль: ");
    String password = scanner.nextLine();

    User newUser = new User(0, name, age, username, password); // Передаємо ID (наприклад, 0)

    // Перевірка даних користувача через валідатор
    if (UserValidator.validateUser(newUser)) {
      // Збереження користувача через UnitOfWork
      unitOfWork.getUserRepository().addUser(newUser);
      System.out.println("Користувача успішно зареєстровано!");
    } else {
      System.out.println("Реєстрація не вдалася. Перевірте дані та спробуйте знову.");
    }
  }

  // Метод для авторизації користувача через UnitOfWork
  private static void authorizeUser(Scanner scanner) {
    System.out.println("Авторизація:");
    System.out.print("Введіть логін: ");
    String username = scanner.nextLine();

    System.out.print("Введіть пароль: ");
    String password = scanner.nextLine();

    // Пошук користувача через UserRepository
    Optional<User> user = unitOfWork.getUserRepository().getAllUsers()
        .stream()
        .filter(u -> u.authorize(username, password))
        .findFirst();

    if (user.isPresent()) {
      System.out.println("Авторизація успішна! Вітаємо, " + user.get().getName());
    } else {
      System.out.println("Авторизація не вдалася. Неправильний логін або пароль.");
    }
  }
}
