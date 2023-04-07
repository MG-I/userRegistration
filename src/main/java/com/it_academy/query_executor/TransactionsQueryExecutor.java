package com.it_academy.query_executor;

import com.it_academy.ApplicationDB;
import com.it_academy.model.Transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionsQueryExecutor {
    public static void printAllTransactions(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Transactions;");
            while (resultSet.next()) {
                System.out.println("TransactionID: " + resultSet.getInt("transactionId"));
                System.out.println("AccountID: " + resultSet.getInt("accountId"));
                System.out.println("Amount: " + resultSet.getInt("amount"));
            }
            statement.close();
            resultSet.close();
            System.out.println("The case is completed, choose the next one!");
        } catch (SQLException e) {
            System.out.println("An error has occurred. Try again!");
            ApplicationDB.printMenu();
        }
    }
    public static void createTransactions(Connection connection, int accountId, Transactions transactions) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Transactions(accountId,amount) VALUES (?,?); ");
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, transactions.getAmount());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("An error has occurred. Try again!");
            ApplicationDB.printMenu();
        }
    }
}
