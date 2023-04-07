package com.it_academy.query_executor;

import com.it_academy.ApplicationDB;
import com.it_academy.model.Accounts;
import com.it_academy.model.Transactions;
import com.it_academy.service.AccountService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountsQueryExecutor {
    public static void printAllAccount(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Accounts;");
            while (resultSet.next()) {
                System.out.println("AccountID: " + resultSet.getInt("accountId"));
                System.out.println("UserID: " + resultSet.getInt("userID"));
                System.out.println("Balance: " + resultSet.getInt("balance"));
                System.out.println("Currency: " + resultSet.getString("currency"));
            }
            statement.close();
            resultSet.close();
            System.out.println("The case is completed, choose the next one!");
        } catch (SQLException e) {
            System.out.println("An error has occurred. Try again!");
            ApplicationDB.printMenu();
        }
    }
    public static void addingAccountToUser(Connection connection, Accounts accounts) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Accounts (userId, balance,currency) VALUES (?,0,?); ");
            preparedStatement.setInt(1, accounts.getUserId());
            preparedStatement.setString(2, accounts.getCurrency());
            preparedStatement.execute();
            preparedStatement.close();
            System.out.println("The case is completed, choose the next one!");
        } catch (SQLException e) {
            System.out.println("An error has occurred. Try again!");
            ApplicationDB.printMenu();
        }
    }
    public static int checkAccountExist(Connection connection, Accounts accounts) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT accountId FROM Accounts WHERE userId = ? AND currency = ?; ");
            preparedStatement.setInt(1, accounts.getUserId());
            preparedStatement.setString(2, accounts.getCurrency());
            ResultSet resultSet = preparedStatement.executeQuery();
            int accountId;
            if (resultSet.next()) {
                accountId = resultSet.getInt("accountId");
                resultSet.close();
                preparedStatement.close();
                return  accountId;
            } else {
                resultSet.close();
                preparedStatement.close();
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("An error has occurred. Try again!");
            ApplicationDB.printMenu();
            return 0;
        }
    }
    public static void replenishmentBalance(Connection connection, int accountId, Transactions transactions) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM Accounts WHERE accountId = ?; ");
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            int balance;
            if (resultSet.next()) {
                balance = resultSet.getInt("balance");
                balance = balance + transactions.getAmount();
                if (balance > 2000000000) {
                    System.out.println("Account limit exceeded");
                    checkAccountExist(connection, AccountService.inputAccount());
                }
                resultSet.close();
                preparedStatement.close();
                updateBalance(connection, accountId, balance);
            }
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }
    public static void decreaseBalance(Connection connection, int accountId, Transactions transactions) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM Accounts WHERE accountId = ?; ");
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            int balance;
            if (resultSet.next()) {
                balance = resultSet.getInt("balance");
                balance = balance - transactions.getAmount();
                if (balance < 0) {
                    System.out.println("Insufficient funds on the account");
                    ApplicationDB.printMenu();
                }
                resultSet.close();
                preparedStatement.close();
                updateBalance(connection, accountId, balance);
            }
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }
    public static void updateBalance(Connection connection, int accountId, int balance) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Accounts SET balance=? WHERE accountId = ?; ");
            preparedStatement.setInt(1, balance);
            preparedStatement.setInt(2, accountId);
            preparedStatement.execute();
            preparedStatement.close();
            System.out.println("The case is completed, choose the next one!");
        } catch (SQLException e) {
            System.out.println("An error has occurred. Try again!");
            ApplicationDB.printMenu();
        }
    }
}


