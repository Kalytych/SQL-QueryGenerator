package com.example.sqlquerygenerator;

/**
 * Клас User представляє користувача системи з основними атрибутами, такими як ID, ім'я, вік, ім'я
 * користувача та пароль. Він також містить методи для авторизації та перевірки валідації даних.
 */
public class User {

  private int id; // Унікальний ідентифікатор користувача
  private String name; // Ім'я користувача
  private int age; // Вік користувача
  private String username; // Ім'я користувача для входу в систему
  private String password; // Пароль користувача

  /**
   * Конструктор класу User для ініціалізації об'єкта користувача з заданими параметрами.
   *
   * @param id       унікальний ідентифікатор користувача
   * @param name     ім'я користувача
   * @param age      вік користувача
   * @param username ім'я користувача для входу в систему
   * @param password пароль користувача
   */
  public User(int id, String name, int age, String username, String password) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.username = username;
    this.password = password;
  }

  /**
   * Отримати унікальний ідентифікатор користувача.
   *
   * @return id користувача
   */
  public int getId() {
    return id;
  }

  /**
   * Встановити унікальний ідентифікатор користувача.
   *
   * @param id унікальний ідентифікатор користувача
   * @throws IllegalArgumentException якщо id менше або рівне 0
   */
  public void setId(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("ID має бути додатнім числом");
    }
    this.id = id;
  }

  /**
   * Отримати ім'я користувача.
   *
   * @return ім'я користувача
   */
  public String getName() {
    return name;
  }

  /**
   * Встановити ім'я користувача.
   *
   * @param name ім'я користувача
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Отримати вік користувача.
   *
   * @return вік користувача
   */
  public int getAge() {
    return age;
  }

  /**
   * Встановити вік користувача.
   *
   * @param age вік користувача
   * @throws IllegalArgumentException якщо вік менше 0
   */
  public void setAge(int age) {
    if (age < 0) {
      throw new IllegalArgumentException("Вік не може бути від'ємним");
    }
    this.age = age;
  }

  /**
   * Отримати ім'я користувача для входу в систему.
   *
   * @return ім'я користувача
   */
  public String getUsername() {
    return username;
  }

  /**
   * Встановити ім'я користувача для входу в систему.
   *
   * @param username ім'я користувача
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Отримати пароль користувача.
   *
   * @return пароль користувача
   */
  public String getPassword() {
    return password;
  }

  /**
   * Встановити пароль користувача.
   *
   * @param password пароль користувача
   * @throws IllegalArgumentException якщо пароль має менше 6 символів
   */
  public void setPassword(String password) {
    if (password.length() < 6) {
      throw new IllegalArgumentException("Пароль має містити щонайменше 6 символів");
    }
    this.password = password;
  }

  /**
   * Перевірити авторизацію користувача за допомогою порівняння ім'я користувача та пароля.
   *
   * @param username ім'я користувача для перевірки
   * @param password пароль користувача для перевірки
   * @return true, якщо ім'я користувача та пароль співпадають; false в іншому випадку
   */
  public boolean authorize(String username, String password) {
    return this.username.equals(username) && this.password.equals(password);
  }

  /**
   * Представлення користувача у вигляді рядка для виведення.
   *
   * @return рядкове представлення користувача
   */
  @Override
  public String toString() {
    return "User{" +
        "id=" + id + // Додано id
        ", name='" + name + '\'' +
        ", age=" + age +
        ", username='" + username + '\'' +
        '}';
  }
}

/**
 * Клас SQLQuery представляє SQL запит разом з його статусом.
 */
class SQLQuery {

  private String query; // SQL запит
  private Status status; // Статус запиту

  /**
   * Конструктор для ініціалізації SQL запиту з заданим статусом.
   *
   * @param query  SQL запит
   * @param status статус запиту
   */
  public SQLQuery(String query, Status status) {
    this.query = query;
    this.status = status;
  }

  /**
   * Отримати SQL запит.
   *
   * @return SQL запит
   */
  public String getQuery() {
    return query;
  }

  /**
   * Встановити SQL запит.
   *
   * @param query SQL запит
   */
  public void setQuery(String query) {
    this.query = query;
  }

  /**
   * Отримати статус запиту.
   *
   * @return статус запиту
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Встановити статус запиту.
   *
   * @param status статус запиту
   */
  public void setStatus(Status status) {
    this.status = status;
  }
}

/**
 * Клас Status представляє статус SQL запиту.
 */
class Status {

  private String statusName; // Назва статусу

  /**
   * Конструктор для ініціалізації статусу за назвою.
   *
   * @param statusName назва статусу
   */
  public Status(String statusName) {
    this.statusName = statusName;
  }

  /**
   * Отримати назву статусу.
   *
   * @return назва статусу
   */
  public String getStatusName() {
    return statusName;
  }

  /**
   * Встановити назву статусу.
   *
   * @param statusName назва статусу
   */
  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }
}
