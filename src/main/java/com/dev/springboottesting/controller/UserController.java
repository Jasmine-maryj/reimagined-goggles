package com.dev.springboottesting.controller;

import com.dev.springboottesting.dto.UserDto;
import com.dev.springboottesting.dto.UserLoginDto;
import com.dev.springboottesting.entity.User;
import com.dev.springboottesting.exceptionhandler.UserNotFoundException;
import com.dev.springboottesting.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody @Valid UserDto userDto){
        userService.addUser(userDto);
        return new ResponseEntity<>("Created new user", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto userLoginDto){
        Boolean isLoggedIn = userService.loginUser(userLoginDto);
        String result = "";
        if (isLoggedIn){
            result = "Logged In Successfully";
        }else{
            result = "Invalid username or password";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/firstName")
    public ResponseEntity<User> getUserByFirstName(@RequestParam("firstName") String firstName){
        User user = userService.getUserByFirstName(firstName);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        User user = userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto, @PathVariable("id") Long id) throws UserNotFoundException {
        User user = userService.updateUser(userDto, id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        Map<String, String> map = new HashMap<>();
        map.put("message", "success");
        return ResponseEntity.ok(map);
    }

}
