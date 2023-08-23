package com.dev.userservice.dto;

import com.dev.userservice.validate.ValidateEmail;
import com.dev.userservice.validate.ValidatePassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {
    @ValidateEmail(message = "Invalid EmailId")
    private String email;

    private String oldPassword;

    @ValidatePassword(message = "Password must contain a digit,a lower case letter, an upper case letter and a special character")
    private String newPassword;
}
