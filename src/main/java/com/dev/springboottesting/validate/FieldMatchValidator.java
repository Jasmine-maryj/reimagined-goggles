package com.dev.springboottesting.validate;

import com.dev.springboottesting.dto.PasswordResetDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object user, ConstraintValidatorContext constraintValidatorContext) {
        PasswordResetDTO uPD = (PasswordResetDTO) user;
        return uPD.getPassword().equals(uPD.getMatchingPassword());
    }

}
