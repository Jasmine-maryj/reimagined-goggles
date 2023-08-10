package com.dev.springboottesting.dto;

import com.dev.springboottesting.validate.FieldMatch;
import com.dev.springboottesting.validate.ValidateEmail;
import com.dev.springboottesting.validate.ValidatePassword;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDTO {
    @ValidateEmail(message = "Invalid emailId")
    private String email;

    @ValidatePassword(message = "Password must contain a digit,a lower case letter, an upper case letter and a special character")
    private String password;

    @NotBlank
    private String matchingPassword;
}
