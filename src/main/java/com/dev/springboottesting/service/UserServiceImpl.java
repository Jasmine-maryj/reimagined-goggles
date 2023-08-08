package com.dev.springboottesting.service;

import com.dev.springboottesting.entity.Token;
import com.dev.springboottesting.dto.UserDto;
import com.dev.springboottesting.dto.UserLoginDto;
import com.dev.springboottesting.entity.User;
import com.dev.springboottesting.exceptionhandler.UserNotFoundException;
import com.dev.springboottesting.repository.TokenRepository;
import com.dev.springboottesting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserDto userDto) {
        User user1 = userRepository.findUserByEmail(userDto.getEmail());
        if(user1 != null){
            throw new UserNotFoundException("User with email already exists.");
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(user);
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
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

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

    @Override
    public boolean loginUser(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmailAndPassword(userLoginDto.getEmail(), passwordEncoder.encode(userLoginDto.getPassword()));
        if(user != null){
            return true;
        }
        return false;
    }

    @Override
    public User getUserByFirstName(String firstName) {
        User user = userRepository.findByFirstName(firstName);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public void saveVerificationToken(User user, String token) {
        Token verificationToken = new Token(user, token);
        tokenRepository.save(verificationToken);
    }
}
