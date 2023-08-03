package com.dev.springboottesting.service;

import com.dev.springboottesting.dto.UserDto;
import com.dev.springboottesting.entity.User;
import com.dev.springboottesting.exceptionhandler.UserNotFoundException;
import com.dev.springboottesting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long userId) throws UserNotFoundException {
        return userRepository.findUserById(userId);
    }

    @Override
    public User updateUser(UserDto userDto, Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        if (user == null){
            throw new UserNotFoundException("User not found");
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        try{
            userRepository.deleteById(id);
        }catch (Exception e){
            throw new UserNotFoundException("User not found");
        }
    }
}
