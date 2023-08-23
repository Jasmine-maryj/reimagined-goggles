package com.dev.userservice.service;

import com.dev.userservice.dto.PasswordResetDTO;
import com.dev.userservice.dto.UserDto;
import com.dev.userservice.dto.UserLoginDto;
import com.dev.userservice.entity.Token;
import com.dev.userservice.entity.User;
import com.dev.userservice.exceptionhandler.UserNotFoundException;

import java.util.List;

public interface UserService {
    User registerUser(UserDto userDto);

    List<User> getAllUsers();

    User findUserById(Long userId) throws UserNotFoundException;

    User updateUser(UserDto userDto, Long id) throws UserNotFoundException;

    void deleteUser(Long id);

    boolean loginUser(UserLoginDto userLoginDto);

    User getUserByFirstName(String firstName);

    void saveVerificationToken(User user, String token);

    String validateUserEmail(String token);

    Token findByToken(String oldToken);

    String resetPassword(PasswordResetDTO passwordResetDTO);

    User findUserByEmail(String email);

    String validateResetPasswordToken(String token);

    User getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    User findUserByEmailAndPassword(String email, String oldPassword);
}
