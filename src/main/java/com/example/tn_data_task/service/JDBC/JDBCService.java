package com.example.tn_data_task.service.JDBC;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
@Slf4j
public class JDBCService {
  public static void complete() {
    String url = "jdbc:postgresql://localhost:5433/postgres";
    String username = "ShevchenkoDN";
    String password = "She031fit";

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      // Получение всех пользователей и вывод фамилий в консоль
      String query = "SELECT full_name FROM person";
      try (Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(query)
      ) {
        while (resultSet.next()) {
          String fullName = resultSet.getString("full_name");
          System.out.println(fullName.split(" ")[0]);
        }
      }

      // Изменение имени всех банков в одной транзакции
      connection.setAutoCommit(false); // Отключаем автоматическую фиксацию транзакции
      try (Statement updateStatement = connection.createStatement()) {
        String updateQuery = "UPDATE bank SET name = 'Новое имя банка'"; // Замените на новое имя
        updateStatement.executeUpdate(updateQuery);
        connection.commit(); // Фиксируем транзакцию
      } catch (SQLException e) {
        connection.rollback(); // Откатываем транзакцию в случае ошибки
      } finally {
        connection.setAutoCommit(true); // Включаем автоматическую фиксацию транзакции обратно
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
