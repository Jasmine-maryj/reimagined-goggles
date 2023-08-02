package com.dev.springboottesting.service;

import com.dev.springboottesting.dto.UserDto;
import com.dev.springboottesting.entity.User;
import com.dev.springboottesting.exceptionhandler.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(UserDto userDto);

    List<User> getAllUsers();

    Optional<User> findUserById(Long userId) throws UserNotFoundException;

    User updateUser(UserDto userDto, Long id) throws UserNotFoundException;

    void deleteUser(Long id);
}
