package com.it_academy.service;

import com.it_academy.model.Accounts;

import java.util.Scanner;
public class AccountService {
    public static int writeUserId;
    public static String writeCurrency;
    public static Accounts inputAccount() {
        Accounts account = new Accounts();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter userId: ");
        if (scanner.hasNextInt()) {
            writeUserId = scanner.nextInt();
        } else {
            System.out.println("The entered value is not a number. Please try again! ");
            return inputAccount();
        }
        account.setUserId(writeUserId);
        scanner.nextLine();
        System.out.println("Enter account currency: ");
        writeCurrency = scanner.nextLine();
        account.setCurrency(writeCurrency);
        return account;
    }
}
