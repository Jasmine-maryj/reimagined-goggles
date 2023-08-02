package com.dev.springboottesting.controller;

import com.dev.springboottesting.dto.UserDto;
import com.dev.springboottesting.entity.User;
import com.dev.springboottesting.exceptionhandler.UserNotFoundException;
import com.dev.springboottesting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        Optional<User> user = userService.findUserById(userId);
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
