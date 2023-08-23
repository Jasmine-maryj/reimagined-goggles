package com.dev.userservice.validate;

import com.dev.userservice.dto.PasswordResetDTO;
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
        return uPD.getPassword().equals(uPD.getNewPassword());
    }

}
