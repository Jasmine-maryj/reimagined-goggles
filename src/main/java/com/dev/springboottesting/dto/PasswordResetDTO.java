package com.dev.springboottesting.dto;

import com.dev.springboottesting.validate.FieldMatch;
import com.dev.springboottesting.validate.ValidateEmail;
import com.dev.springboottesting.validate.ValidatePassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@FieldMatch(message = "Password must match!")
@Data
@NoArgsConstructor
public class PasswordResetDTO {
    @ValidateEmail(message = "Invalid emailId")
    private String email;

    @ValidatePassword(message = "Password must contain a digit,a lower case letter, an upper case letter and a special character")
    private String password;

    @NotBlank
    private String matchingPassword;
}
