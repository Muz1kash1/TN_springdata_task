package com.example.tn_data_task.JDBC;

import com.example.tn_data_task.service.JDBC.JDBCService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JDBCServiceTest {

  @Test @DisplayName("Тест на работу JDBC сервиса") void complete() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalPrintStream = System.out;
    System.setOut(new PrintStream(outputStream));

    String url = "jdbc:postgresql://localhost:5433/postgres";
    String username = "ShevchenkoDN";
    String password = "She031fit";

    try (Connection connection = DriverManager.getConnection(url, username, password);
         Statement statement = connection.createStatement()
    ) {

      // Создание таблиц
      String createPersonTable = "CREATE TABLE person (id SERIAL PRIMARY KEY, full_name VARCHAR)";
      statement.executeUpdate(createPersonTable);

      String createBankTable = "CREATE TABLE bank (id SERIAL PRIMARY KEY, name VARCHAR)";
      statement.executeUpdate(createBankTable);

      String createPersonBankTable = "CREATE TABLE person_bank (id BIGSERIAL PRIMARY KEY, "
        + "person_id INTEGER NOT NULL REFERENCES person, bank_id INTEGER NOT NULL REFERENCES bank, "
        + "UNIQUE (person_id, bank_id))";
      statement.executeUpdate(createPersonBankTable);

      // Вставка данных
      String insertBankData = "INSERT INTO bank (id, name) "
        + "VALUES (1, 'Сбер'), (2, 'Тинькофф'), (3, 'Хоум Банк'), (4, 'ВТБ')";
      statement.executeUpdate(insertBankData);

      String insertPersonData = "INSERT INTO person (id, full_name) "
        + "VALUES (1, 'Иванов Сидор Петрович'), (2, 'Сидоров Петр Иванович'), "
        + "(3, 'Петров Иван Сидорович'), (4, 'Наличный Артем Андреевич')";
      statement.executeUpdate(insertPersonData);

      String insertPersonBankData = "INSERT INTO person_bank (person_id, bank_id) "
        + "VALUES (1, 1), (1, 3), (2, 2), (2, 3), (2, 4), (3, 1), (3, 4)";
      statement.executeUpdate(insertPersonBankData);

      JDBCService.complete();

      // Проверка ожидаемого вывода
      String expectedOutput = "Иванов\r" + "Сидоров\r" + "Петров\r" + "Наличный\r";

      assertEquals(expectedOutput.replaceAll("\\P{Print}", ""), outputStream.toString().replaceAll("\\P{Print}", ""));
      String dropTables = "DROP TABLE person_bank;\n" + "\n" + "DROP TABLE person;\n" + "\n" + "DROP TABLE bank;";
      statement.executeUpdate(dropTables);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // Восстанавливаем стандартный вывод в консоль
      System.setOut(originalPrintStream);
    }
  }
}