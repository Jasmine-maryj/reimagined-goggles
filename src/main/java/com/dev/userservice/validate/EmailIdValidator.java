package com.dev.userservice.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailIdValidator implements ConstraintValidator<ValidateEmail, String> {

    String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

    @Override
    public void initialize(ValidateEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String inputEmail, ConstraintValidatorContext constraintValidatorContext) {
        return validateEmail(inputEmail, emailRegex);
    }

    private boolean validateEmail(String inputEmail, String emailRegex) {
        return Pattern.compile(emailRegex)
                .matcher(inputEmail)
                .matches();
    }
}
