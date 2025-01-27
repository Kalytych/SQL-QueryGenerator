package com.example.sqlquerygenerator;

public class User {

  private int id; // Додано поле id
  private String name;
  private int age;
  private String username;
  private String password;

  // Конструктор
  public User(int id, String name, int age, String username, String password) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.username = username;
    this.password = password;
  }

  // Геттери та сеттери для всіх полів
  public int getId() {
    return id; // Getter для id
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

  // Метод для перевірки авторизації
  public boolean authorize(String username, String password) {
    return this.username.equals(username) && this.password.equals(password);
  }

  // Метод для представлення інформації про користувача у вигляді рядка
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

class SQLQuery {

  private String query;
  private Status status; // Статус запиту

  public SQLQuery(String query, Status status) {
    this.query = query;
    this.status = status;
  }

  // Гетери та сетери
  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}

class Status {

  private String statusName;

  public Status(String statusName) {
    this.statusName = statusName;
  }

  // Гетери та сетери
  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }
}
