package com.it_academy.service;

import com.it_academy.model.Accounts;

import java.util.Scanner;
public class AccountService {
    public static int WRITE_USER_ID;
    public static String WRITE_CURRENCY;
    public static Accounts inputAccount() {
        Accounts account = new Accounts();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter userId: ");
        if (scanner.hasNextInt()) {
            WRITE_USER_ID = scanner.nextInt();
        } else {
            System.out.println("The entered value is not a number. Please try again! ");
            return inputAccount();
        }
        account.setUserId(WRITE_USER_ID);
        scanner.nextLine();
        System.out.println("Enter account currency: ");
        WRITE_CURRENCY = scanner.nextLine();
        account.setCurrency(WRITE_CURRENCY);
        return account;
    }
}
