package com.dev.springboottesting.service;

import com.dev.springboottesting.dto.UserDto;
import com.dev.springboottesting.entity.User;
import com.dev.springboottesting.exceptionhandler.UserNotFoundException;
import com.dev.springboottesting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<User> findUserById(Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findUserById(userId);
        if(!user.isPresent()){
            throw new UserNotFoundException("User Not found");
        }
        return user;
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
