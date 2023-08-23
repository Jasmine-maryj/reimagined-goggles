package com.dev.userservice.events;

import com.dev.userservice.entity.User;
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
