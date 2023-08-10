package com.dev.springboottesting.validate;

import jakarta.validation.Payload;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.*;

/**
 * Validation annotation to validate that 2 fields have the same value.
 * An array of fields and their matching confirmation fields can be supplied.
 *
 * Example, compare 1 pair of fields:
 * @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
 *
 * Example, compare more than 1 pair of fields:
 * @FieldMatch.List({
 *   @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
 *   @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")})
 */

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Validated(value = FieldMatchValidator.class)
public @interface FieldMatch {
    String message() default "{constraints.fieldmatch}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String first();
    String second();

    @Retention(value = RetentionPolicy.RUNTIME)
    @Documented
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @interface List{
        FieldMatch[] value();
    }
}
