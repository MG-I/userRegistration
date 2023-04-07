package com.it_academy.service;

import com.it_academy.model.Transactions;
import java.util.Scanner;
public class TransactionService {
    public static Transactions inputAmount() {
        Transactions transactions = new Transactions();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount: ");
        int amount;
        if(scanner.hasNextInt()) {
             amount = scanner.nextInt();
            if (amount > 100000000) {
                System.out.println("Amount greater than 100 000 000. Please enter an amount less.");
                return inputAmount();
            }
        }else{
            System.out.println("The entered value is not a number. Please try again! ");
            return inputAmount();
        }
        transactions.setAmount(amount);
        return transactions;
    }
}
