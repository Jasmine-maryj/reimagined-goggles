package com.dev.springboottesting.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidatePassword {
    String message() default " Password must contain a digit,a lower case letter, an upper case letter and a special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
