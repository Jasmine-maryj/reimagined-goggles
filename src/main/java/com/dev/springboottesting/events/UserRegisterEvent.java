package com.dev.springboottesting.events;

import com.dev.springboottesting.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserRegisterEvent extends ApplicationEvent {
    private User user;
    private String applicationUrl;

    public UserRegisterEvent(User user, String applicationUrl){
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
