package com.example.oopsProject.Security;


import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class passwordencoder {
    public static String encode(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes());
    }

}
