package com.dev.springboottesting.controller;

import com.dev.springboottesting.config.Constants;
import com.dev.springboottesting.dto.UserDto;
import com.dev.springboottesting.dto.UserLoginDto;
import com.dev.springboottesting.entity.User;
import com.dev.springboottesting.exceptionhandler.UserNotFoundException;
import com.dev.springboottesting.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody @Valid UserDto userDto){
        User user = userService.registerUser(userDto);
        return new ResponseEntity<>(generateToken(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(HttpServletRequest request, @RequestBody UserLoginDto userLoginDto){
        int userId = (int) request.getAttribute("id");
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

    private Map<String, String> generateToken(User user){
        try {
            long time = System.currentTimeMillis();
            String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(time + Constants.TOKEN_EXPIRATION_TIME))
                    .claim("id", user.getId())
                    .claim("firstName", user.getFirstName())
                    .claim("lastName", user.getLastName())
                    .compact();

            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            return map;
        }catch (Exception e){
            throw new UserNotFoundException("Bad request");
        }
    }

}
