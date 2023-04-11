package com.it_academy;

import com.it_academy.model.Accounts;
import com.it_academy.model.Transactions;
import com.it_academy.query_executor.AccountsQueryExecutor;
import com.it_academy.query_executor.UsersQueryExecutor;
import com.it_academy.service.AccountService;
import com.it_academy.service.TransactionService;
import com.it_academy.service.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import static com.it_academy.query_executor.AccountsQueryExecutor.addingAccountToUser;
import static com.it_academy.query_executor.AccountsQueryExecutor.decreaseBalance;
import static com.it_academy.query_executor.AccountsQueryExecutor.printAllAccount;
import static com.it_academy.query_executor.AccountsQueryExecutor.replenishmentBalance;
import static com.it_academy.query_executor.TransactionsQueryExecutor.createTransactions;
import static com.it_academy.query_executor.TransactionsQueryExecutor.printAllTransactions;
import static com.it_academy.query_executor.UsersQueryExecutor.printAllUsers;

public class ApplicationDB {
    private static final String DATA_BASE_URL =
            "jdbc:sqlite:" + System.getProperty("user.dir") + "\\src\\main\\resources\\Users.db";

    public static void main(String[] args) {
        printMenu();
        if (isDriverExists()) {
            try {
                Connection connection = DriverManager.getConnection(DATA_BASE_URL);
                String action;
                do {
                    action = new Scanner(System.in).nextLine();
                    switch (action) {
                        case "1":
                            UsersQueryExecutor.registerUser(connection, UserService.inputUserName());
                            break;
                        case "2": {
                            int accountId = AccountsQueryExecutor.checkAccountExist(connection, AccountService.inputAccount());
                            if (accountId == 0) {
                                Accounts account = new Accounts();
                                account.setUserId(AccountService.WRITE_USER_ID);
                                account.setCurrency(AccountService.WRITE_CURRENCY);
                                addingAccountToUser(connection,account);
                            } else {
                                System.out.println("An account in this currency already exists for this user!");
                                printMenu();
                            }
                            break;
                        }
                        case "3": {
                            int accountId = AccountsQueryExecutor.checkAccountExist(connection, AccountService.inputAccount());
                            if (accountId != 0) {
                                Transactions amount = TransactionService.inputAmount();
                                createTransactions(connection, accountId, amount);
                                replenishmentBalance(connection, accountId, amount);
                            } else {
                                System.out.println("Such an account does not exist. You can create it in case \"2\"");
                                printMenu();
                            }
                            break;
                        }
                        case "4": {
                            int accountId = AccountsQueryExecutor.checkAccountExist(connection, AccountService.inputAccount());
                            if (accountId != 0) {
                                Transactions amount = TransactionService.inputAmount();
                                createTransactions(connection, accountId, amount);
                                decreaseBalance(connection, accountId, amount);
                            } else {
                                printMenu();
                            }
                            break;
                        }
                        case "5":
                            printAllUsers(connection);
                            break;
                        case "6":
                            printAllAccount(connection);
                            break;
                        case "7":
                            printAllTransactions(connection);
                            break;
                    }
                } while (!"8".equals(action));
                connection.close();
            } catch (SQLException e) {
                System.out.println("DB is not connected");
            }
        }
    }
    public static boolean isDriverExists() {
        try {
            Class.forName("org.sqlite.JDBC");
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver was not found");
            return false;
        }
    }
    public static void printMenu() {
        System.out.println("1 - New user registration");
        System.out.println("2 - Adding an account to a user");
        System.out.println("3 - Topping up an existing account");
        System.out.println("4 - Withdrawal of funds from an existing account");
        System.out.println("5 - print all users");
        System.out.println("6 - print all account ");
        System.out.println("7 - print all transactions ");
        System.out.println("8 - Exit");
    }
}


