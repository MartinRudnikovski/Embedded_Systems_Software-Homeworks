package com.example.zad2.model;

import lombok.NonNull;

public class User {
    public static String password = "123";

    public static User validatePassword(@NonNull String password){
        if (!password.equals(User.password))
            return null;
        return new User();
    }

    public static void changePassword(@NonNull String newPassword){
        password = newPassword;
    }
}
