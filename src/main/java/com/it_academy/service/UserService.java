package com.it_academy.service;

import com.it_academy.model.Users;

import java.util.Scanner;
public class UserService {
    public static Users inputUserName() {
        Users user = new Users();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter user name: ");
            user.setName(scanner.nextLine());
            System.out.println("Enter user address: ");
            user.setAddress(scanner.nextLine());
            return user;
    }
}
