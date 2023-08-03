package com.dev.springboottesting.service;

import com.dev.springboottesting.dto.UserDto;
import com.dev.springboottesting.entity.User;
import com.dev.springboottesting.exceptionhandler.UserNotFoundException;

import java.util.List;

public interface UserService {
    void addUser(UserDto userDto);

    List<User> getAllUsers();

    User findUserById(Long userId) throws UserNotFoundException;

    User updateUser(UserDto userDto, Long id) throws UserNotFoundException;

    void deleteUser(Long id);
}
