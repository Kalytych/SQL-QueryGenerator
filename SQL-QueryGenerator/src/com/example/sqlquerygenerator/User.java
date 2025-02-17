package com.example.sqlquerygenerator;

/**
 * Клас User представляє користувача системи з основними атрибутами, такими як ID, ім'я, вік, ім'я
 * користувача та пароль. Він також містить методи для авторизації та перевірки валідації даних.
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

  private int id; // Унікальний ідентифікатор користувача
  private String name; // Ім'я користувача
  private int age; // Вік користувача
  private String username; // Ім'я користувача для входу в систему
  private String password; // Пароль користувача

  /**
   * Конструктор класу User з анотацією @JsonCreator для коректної десеріалізації з JSON.
   *
   * @param id       унікальний ідентифікатор користувача
   * @param name     ім'я користувача
   * @param age      вік користувача
   * @param username ім'я користувача для входу в систему
   * @param password пароль користувача
   */
  @JsonCreator
  public User(
      @JsonProperty("id") int id,
      @JsonProperty("name") String name,
      @JsonProperty("age") int age,
      @JsonProperty("username") String username,
      @JsonProperty("password") String password
  ) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.username = username;
    this.password = password;
  }

  /**
   * Порожній конструктор для Jackson.
   */
  public User() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("ID має бути додатнім числом");
    }
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    if (age < 0) {
      throw new IllegalArgumentException("Вік не може бути від'ємним");
    }
    this.age = age;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    if (password.length() < 6) {
      throw new IllegalArgumentException("Пароль має містити щонайменше 6 символів");
    }
    this.password = password;
  }

  public boolean authorize(String username, String password) {
    return this.username.equals(username) && this.password.equals(password);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", age=" + age +
        ", username='" + username + '\'' +
        '}';
  }
}

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
