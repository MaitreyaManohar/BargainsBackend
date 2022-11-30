package com.example.oopsProject.Security;

import net.bytebuddy.utility.RandomString;

import java.util.Random;

public class PwdGenerator {
    public static String randomPwdGenerator(){
        String listOfAllCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()/?,.<>;':[]{}`~|";
        StringBuilder newPwd = new StringBuilder();
        Random random = new Random();
        for(int i = 0;i<7;i++){
            newPwd.append(listOfAllCharacters.charAt(random.nextInt(listOfAllCharacters.length())));
        }
        return newPwd.toString();
    }
}
