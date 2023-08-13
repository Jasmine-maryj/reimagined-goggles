package com.dev.springboottesting.controller;

import com.dev.springboottesting.dto.ChangePasswordDTO;
import com.dev.springboottesting.dto.PasswordResetDTO;
import com.dev.springboottesting.dto.UserDto;
import com.dev.springboottesting.dto.UserLoginDto;
import com.dev.springboottesting.entity.Token;
import com.dev.springboottesting.entity.User;
import com.dev.springboottesting.events.UserRegisterEvent;
import com.dev.springboottesting.exceptionhandler.UserNotFoundException;
import com.dev.springboottesting.mappers.Converter;
import com.dev.springboottesting.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    private Converter converter;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody @Valid UserDto userDto, HttpServletRequest request){
        User user = userService.registerUser(userDto);
        applicationEventPublisher.publishEvent(new UserRegisterEvent(user, applicationUrl(request)));
        Map<String, String> map = new HashMap<>();
        map.put("message", "Registered Successfully, Verification link sent, Verify your email address to login");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }


    @GetMapping("/verificationToken")
    public ResponseEntity<Map<String, String>> validateUserEmail(@RequestParam("token") String token){
        String result = userService.validateUserEmail(token);
        Map<String, String> map = new HashMap<>();
        if(result.equalsIgnoreCase("valid")){
            map.put("message", result);
        }else {
            map.put("message", "Bad Request");
        }
        return ResponseEntity.ok(map);
    }

    /**
     * /
     * @param oldToken
     * @param request
     * its working
     */
    @GetMapping("/resendVerification")
    public void resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request){
        Token token = userService.findByToken(oldToken);
        sendVerificationTokenLinkToEmail(token, applicationUrl(request));
    }

    @PostMapping("/login/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody @Valid PasswordResetDTO passwordResetDTO, HttpServletRequest request){
        User user = userService.findUserByEmail(passwordResetDTO.getEmail());
        String url = "";
        Map<String, String> map = new HashMap<>();
        if(user != null){
            Token token = new Token(user);
            String passwordVerificationToken = token.getToken();
            url = sendPasswordResetLinkToEmail(applicationUrl(request), passwordVerificationToken);

        }
        map.put("message", "reset password link has been sent to your email");
        return ResponseEntity.ok(map);
    }

    @GetMapping("/save-password")
    public ResponseEntity<Map<String, String>> savePassword(@RequestParam("token") String token, HttpServletRequest request, @RequestBody PasswordResetDTO passwordResetDTO){
        String result = userService.validateResetPasswordToken(token);
        Map<String, String> map = new HashMap<>();


        if(!result.equalsIgnoreCase("valid")){
            map.put("message", result);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserByPasswordResetToken(token);
        if (user != null){
            userService.changePassword(user, passwordResetDTO.getNewPassword());
            map.put("message", "Password changed successfully");
            return ResponseEntity.ok(map);
        }

        map.put("message", "Invalid Token");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, HttpServletRequest request){
        User user = userService.findUserByEmailAndPassword(changePasswordDTO.getEmail(), changePasswordDTO.getOldPassword());
        userService.changePassword(user, changePasswordDTO.getNewPassword());
        Map<String, String> map = new HashMap<>();
        map.put("message", "Password Changed Successfully!");
        return ResponseEntity.ok(map);
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
    public ResponseEntity<UserDto> getUserByFirstName(@RequestParam("firstName") String firstName){
        User user = userService.getUserByFirstName(firstName);
        UserDto userDto = converter.asDTO(user);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        List<UserDto> userDtoList = converter.asDTOList(userList);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        User user = userService.findUserById(userId);
        UserDto userDto = converter.asDTO(user);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDtoRequest, @PathVariable("id") Long id) throws UserNotFoundException {
        User user = userService.updateUser(userDtoRequest, id);
        UserDto userDtoResponse = converter.asDTO(user);
        return ResponseEntity.ok(userDtoResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        Map<String, String> map = new HashMap<>();
        map.put("message", "success");
        return ResponseEntity.ok(map);
    }


    private String applicationUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName()
                + ":"
                + request.getServerPort()
                + request.getContextPath();
    }

    private void sendVerificationTokenLinkToEmail(Token token, String applicationUrl) {
        String url = applicationUrl + "/verificationToken?token="+token;
        log.info("Click below link to verify your email address:" + url);
    }


    private String sendPasswordResetLinkToEmail(String applicationUrl, String passwordVerificationToken) {
        String url = applicationUrl + "save-password/token?" + passwordVerificationToken;
        log.info("Click below link to reset your password" + url);
        return url;
    }

}
