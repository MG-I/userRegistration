package com.it_academy.query_executor;

import com.it_academy.ApplicationDB;
import com.it_academy.model.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class UsersQueryExecutor {
    public static void printAllUsers(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users;");
            while (resultSet.next()) {
                System.out.println("UserID: " + resultSet.getInt("userId"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Address: " + resultSet.getString("address"));
            }
            statement.close();
            resultSet.close();
            System.out.println("The case is completed, choose the next one!");
        } catch (SQLException e) {
            System.out.println("An error has occurred. Try again!");
            ApplicationDB.printMenu();
        }
    }
    public static void registerUser(Connection connection, Users user) {
        String str = user.getName();
        if (str.equals("")) {
            System.out.println("The username cannot be empty. Try again!");
            ApplicationDB.printMenu();
        } else {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, address) VALUES (?,?) ")) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getAddress());
                preparedStatement.execute();
                preparedStatement.close();
                System.out.println("The case is completed, choose the next one!");
            } catch (SQLException e) {
                System.out.println("An error has occurred. Try again!");
                ApplicationDB.printMenu();
            }
        }
    }
}
