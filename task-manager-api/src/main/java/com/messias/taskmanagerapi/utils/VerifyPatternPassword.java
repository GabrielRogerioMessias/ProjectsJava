package com.messias.taskmanagerapi.utils;


import com.messias.taskmanagerapi.services.exceptions.PasswordIsNotPatterException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class VerifyPatternPassword {
    String regexPattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d])[a-zA-Z\\d[^a-zA-Z\\d]]{8,}$";
    Pattern pattern = Pattern.compile(regexPattern);

    public Boolean verifyPassword(String password) {
        Matcher matcher = pattern.matcher(password);
        if (matcher.find() && !password.isEmpty()) {
            return true;
        } else {
            throw new PasswordIsNotPatterException();
        }
    }
}
