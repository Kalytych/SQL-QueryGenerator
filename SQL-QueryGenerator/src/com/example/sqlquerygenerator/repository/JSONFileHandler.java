package com.example.sqlquerygenerator.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Клас для роботи з файлами JSON.
 */
public class JSONFileHandler {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Записує список об'єктів у JSON файл.
   *
   * @param fileName шлях до файлу
   * @param data список даних для запису
   * @param <T> тип об'єкта
   * @return true, якщо запис успішний, false в разі помилки
   */
  public static <T> boolean writeToJsonFile(String fileName, List<T> data) {
    File file = new File(fileName);

    try {
      // Створюємо файл і директорії, якщо вони не існують
      if (!file.exists()) {
        file.getParentFile().mkdirs(); // Створюємо директорії, якщо потрібно
        file.createNewFile();
      }

      // Записуємо дані в файл з форматуванням
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
      System.out.println("Дані успішно збережені у файл: " + file.getAbsolutePath());
      return true; // Повертаємо true, якщо все успішно

    } catch (IOException e) {
      System.err.println("Помилка запису у JSON файл: " + e.getMessage());
      return false; // Повертаємо false в разі помилки
    }
  }

  /**
   * Читає список об'єктів з JSON файлу.
   *
   * @param fileName шлях до файлу
   * @param clazz клас об'єкта, який ми хочемо отримати
   * @param <T> тип об'єкта
   * @return список об'єктів з JSON файлу
   */
  public static <T> List<T> readFromJsonFile(String fileName, Class<T> clazz) {
    File file = new File(fileName);

    if (!file.exists() || file.length() == 0) {
      System.err.println("Файл " + file.getAbsolutePath() + " не існує або порожній. Повертаємо пустий список.");
      return new ArrayList<>();
    }

    try {
      // Читаємо список об'єктів з JSON файлу
      return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    } catch (IOException e) {
      System.err.println("Помилка читання JSON файлу: " + e.getMessage());
      return new ArrayList<>();
    }
  }
}
