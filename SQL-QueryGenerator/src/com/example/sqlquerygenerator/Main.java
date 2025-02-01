package com.example.sqlquerygenerator;

import com.example.sqlquerygenerator.repository.JSONFileHandler;
import com.example.sqlquerygenerator.repository.SQLRequest;
import com.example.sqlquerygenerator.repository.UserRepository;
import com.example.sqlquerygenerator.unitofwork.UnitOfWork;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Головний клас програми, який надає користувачу можливість реєстрації, авторизації,
 * а також генерації SQL-запитів.
 */
public class Main {

  private static final UnitOfWork unitOfWork = new UnitOfWork(new UserRepository());
  private static final String JSON_USER_FILE = "src/com/example/SqlQueryGenerator/users.json";

  /**
   * Точка входу в програму.
   * Запускає головне меню та обробляє вибір користувача.
   *
   * @param args аргументи командного рядка (не використовуються)
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    loadUsersFromJSON();

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
          saveUsersToJSON();
          System.out.println("До побачення!");
          return;
        }
        default -> System.out.println("Неправильний вибір. Спробуйте знову.");
      }
    }
  }

  /**
   * Реєстрація нового користувача.
   * Запитує користувача дані для реєстрації та додає його до списку користувачів.
   *
   * @param scanner об'єкт для зчитування введених даних
   */
  private static void registerUser(Scanner scanner) {
    System.out.println("Реєстрація:");
    System.out.print("Введіть ім'я: ");
    String name = scanner.nextLine();

    System.out.print("Введіть вік: ");
    int age = scanner.nextInt();
    scanner.nextLine();

    System.out.print("Введіть логін: ");
    String username = scanner.nextLine();

    // Перевірка на існуючий логін
    if (unitOfWork.getUserRepository().getAllUsers().stream()
        .anyMatch(u -> u.getUsername().equals(username))) {
      System.out.println("Такий логін вже існує. Спробуйте ще раз.");
      return;
    }

    System.out.print("Введіть пароль: ");
    String password = scanner.nextLine();

    User newUser = new User(0, name, age, username, password);

    if (UserValidator.validateUser(newUser)) {
      unitOfWork.getUserRepository().addUser(newUser);
      saveUsersToJSON();
      System.out.println("Користувача успішно зареєстровано!");
    } else {
      System.out.println("Реєстрація не вдалася. Перевірте дані та спробуйте знову.");
    }
  }

  /**
   * Авторизація користувача.
   * Перевіряє, чи існує користувач з введеним логіном і паролем.
   *
   * @param scanner об'єкт для зчитування введених даних
   */
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

  /**
   * Генерація SQL-запитів.
   * Запитує користувача тип запиту та генерує SQL-запит відповідно до вибору.
   *
   * @param scanner об'єкт для зчитування введених даних
   */
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
    String[] columns = scanner.nextLine().split(", *");

    SQLRequest sqlRequest = new SQLRequest(tableName, Arrays.asList(columns), null, null);

    switch (choice) {
      case 1 -> {
        System.out.print("Введіть значення (через кому): ");
        String[] values = scanner.nextLine().split(", *");
        sqlRequest = new SQLRequest(tableName, Arrays.asList(columns), Arrays.asList(values), null);
        String insertQuery = sqlRequest.generateInsertQuery();
        System.out.println("Згенерований INSERT-запит:");
        System.out.println(insertQuery);
      }
      case 2 -> {
        System.out.print("Введіть умову (або залиште порожнім): ");
        String condition;
        while (true) {
          condition = scanner.nextLine();
          if (isValidCondition(condition)) {
            break; // Якщо умова правильна, вийдемо з циклу
          } else {
            System.out.println("Неправильна умова. Спробуйте ще раз.");
          }
        }
        sqlRequest = new SQLRequest(tableName, Arrays.asList(columns), null, condition);
        String selectQuery = sqlRequest.generateSelectQuery();
        System.out.println("Згенерований SELECT-запит:");
        System.out.println(selectQuery);
      }
      case 3 -> {
        System.out.print("Введіть нові значення (через кому): ");
        String[] values = scanner.nextLine().split(", *");
        System.out.print("Введіть умову (або залиште порожнім): ");
        String condition;
        while (true) {
          condition = scanner.nextLine();
          if (isValidCondition(condition)) {
            break; // Якщо умова правильна, вийдемо з циклу
          } else {
            System.out.println("Неправильна умова. Спробуйте ще раз.");
          }
        }
        sqlRequest = new SQLRequest(tableName, Arrays.asList(columns), Arrays.asList(values), condition);
        String updateQuery = sqlRequest.generateUpdateQuery();
        System.out.println("Згенерований UPDATE-запит:");
        System.out.println(updateQuery);
      }
      case 4 -> {
        System.out.print("Введіть умову (або залиште порожнім): ");
        String condition;
        while (true) {
          condition = scanner.nextLine();
          if (isValidCondition(condition)) {
            break; // Якщо умова правильна, вийдемо з циклу
          } else {
            System.out.println("Неправильна умова. Спробуйте ще раз.");
          }
        }
        sqlRequest = new SQLRequest(tableName, Arrays.asList(columns), null, condition);
        String deleteQuery = sqlRequest.generateDeleteQuery();
        System.out.println("Згенерований DELETE-запит:");
        System.out.println(deleteQuery);
      }
      default -> System.out.println("Неправильний вибір. Спробуйте знову.");
    }
  }

  /**
   * Метод для перевірки правильності умови.
   * Можна розширити регулярний вираз для складніших перевірок.
   *
   * @param condition умова, яку перевіряємо
   * @return true, якщо умова правильна, false — якщо неправильна
   */
  private static boolean isValidCondition(String condition) {
    // Простий приклад перевірки: перевіряємо, чи умова має формат "стовпець оператор значення"
    return condition.matches("^[a-zA-Z_][a-zA-Z0-9_]*\\s*=[^\\s].*");
  }

  /**
   * Завантаження користувачів з JSON файлу.
   */
  private static void loadUsersFromJSON() {
    List<User> users = JSONFileHandler.readFromJsonFile(JSON_USER_FILE, User.class);
    if (users != null) {
      users.forEach(unitOfWork.getUserRepository()::addUser);
      System.out.println("Користувачі успішно завантажені з файлу JSON.");
    }
  }

  /**
   * Збереження користувачів у JSON файл.
   */
  private static void saveUsersToJSON() {
    List<User> users = unitOfWork.getUserRepository().getAllUsers();
    if (JSONFileHandler.writeToJsonFile(JSON_USER_FILE, users)) {
      System.out.println("Користувачі успішно збережені у файл.");
    } else {
      System.err.println("Помилка збереження користувачів у файл.");
    }
  }
}
