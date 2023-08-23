package com.dev.userservice.dto;

import com.dev.userservice.validate.FieldMatch;
import com.dev.userservice.validate.ValidateEmail;
import com.dev.userservice.validate.ValidatePassword;
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

    @ValidatePassword(message = "Password must contain a digit,a lower case letter, an upper case letter and a special character")
    private String newPassword;
}
