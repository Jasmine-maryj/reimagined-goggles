package com.dev.springboottesting.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidatePassword, String> {

    private String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    @Override
    public void initialize(ValidatePassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String userPassword, ConstraintValidatorContext constraintValidatorContext) {
        return validatePassword(userPassword, passwordRegex);
    }

    private boolean validatePassword(String userPassword, String passwordRegex) {
        return Pattern.compile(passwordRegex)
                .matcher(userPassword)
                .matches();
    }
}
