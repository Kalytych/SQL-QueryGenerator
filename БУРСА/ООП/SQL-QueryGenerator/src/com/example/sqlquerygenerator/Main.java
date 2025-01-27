package com.example.sqlquerygenerator;

import com.example.sqlquerygenerator.repository.SQLRequest;
import com.example.sqlquerygenerator.repository.UserRepository;
import com.example.sqlquerygenerator.unitofwork.UnitOfWork;
import java.util.Arrays;
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
      System.out.println("3. Генерація SQL-запитів");
      System.out.println("4. Вихід");
      int choice = scanner.nextInt();
      scanner.nextLine(); // Очистка буфера

      switch (choice) {
        case 1 -> registerUser(scanner);
        case 2 -> authorizeUser(scanner);
        case 3 -> generateSQL(scanner);
        case 4 -> {
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

    if (UserValidator.validateUser(newUser)) {
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

  // Метод для генерації SQL-запитів
  private static void generateSQL(Scanner scanner) {
    System.out.println("Генерація SQL-запитів:");
    System.out.println("Оберіть тип запиту:");
    System.out.println("1. INSERT");
    System.out.println("2. SELECT");
    System.out.println("3. UPDATE");
    System.out.println("4. DELETE");

    int choice = scanner.nextInt();
    scanner.nextLine(); // Очистка буфера

    System.out.print("Введіть назву таблиці: ");
    String tableName = scanner.nextLine();

    System.out.print("Введіть стовпці (через кому): ");
    String[] columns = scanner.nextLine().split(",\\s*");

    SQLRequest sqlRequest = new SQLRequest(tableName, Arrays.asList(columns), null,
        null); // Ініціалізація об'єкта SQLRequest

    switch (choice) {
      case 1 -> {
        System.out.print("Введіть значення (через кому): ");
        String[] values = scanner.nextLine().split(",\\s*");
        sqlRequest = new SQLRequest(tableName, Arrays.asList(columns), Arrays.asList(values),
            null); // Ініціалізація для INSERT
        String insertQuery = sqlRequest.generateInsertQuery(); // Генерація INSERT-запиту
        System.out.println("Згенерований INSERT-запит:");
        System.out.println(insertQuery);
      }
      case 2 -> {
        System.out.print("Введіть умову (або залиште порожнім): ");
        String condition = scanner.nextLine();
        sqlRequest = new SQLRequest(tableName, Arrays.asList(columns), null,
            condition); // Ініціалізація для SELECT
        String selectQuery = sqlRequest.generateSelectQuery(); // Генерація SELECT-запиту
        System.out.println("Згенерований SELECT-запит:");
        System.out.println(selectQuery);
      }
      case 3 -> {
        System.out.print("Введіть нові значення (через кому): ");
        String[] values = scanner.nextLine().split(",\\s*");
        System.out.print("Введіть умову (або залиште порожнім): ");
        String condition = scanner.nextLine();
        sqlRequest = new SQLRequest(tableName, Arrays.asList(columns), Arrays.asList(values),
            condition); // Ініціалізація для UPDATE
        String updateQuery = sqlRequest.generateUpdateQuery(); // Генерація UPDATE-запиту
        System.out.println("Згенерований UPDATE-запит:");
        System.out.println(updateQuery);
      }
      case 4 -> {
        System.out.print("Введіть умову (або залиште порожнім): ");
        String condition = scanner.nextLine();
        sqlRequest = new SQLRequest(tableName, Arrays.asList(columns), null,
            condition); // Ініціалізація для DELETE
        String deleteQuery = sqlRequest.generateDeleteQuery(); // Генерація DELETE-запиту
        System.out.println("Згенерований DELETE-запит:");
        System.out.println(deleteQuery);
      }
      default -> System.out.println("Неправильний вибір. Спробуйте знову.");
    }
  }
}
