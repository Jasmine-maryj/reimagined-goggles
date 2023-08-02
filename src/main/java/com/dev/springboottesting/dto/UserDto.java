package com.dev.springboottesting.dto;

import com.dev.springboottesting.validate.ValidateEmail;
import com.dev.springboottesting.validate.ValidatePassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "firstname must be provided")
    private String firstName;
    private String lastName;

    @ValidateEmail(message = "Invalid emailId")
    private String email;

    @ValidatePassword(message = "Password must contain a digit,a lower case letter, an upper case letter and a special character")
    private String password;
}
