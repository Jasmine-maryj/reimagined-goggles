package com.dev.userservice.listener;

import com.dev.userservice.entity.Token;
import com.dev.userservice.entity.User;
import com.dev.userservice.events.UserRegisterEvent;
import com.dev.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRegisterCompleteEventListener implements ApplicationListener<UserRegisterEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        User user = event.getUser();
        Token verificationToken = new Token(user);
//        verificationToken.getToken();
        String token = verificationToken.getToken();
        log.info(token);
        userService.saveVerificationToken(user, token);

        String url = event.getApplicationUrl() + "/verificationToken?token="+token;
        log.info("Click below link to verify your email address:" + url);
    }
}
