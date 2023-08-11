package com.dev.springboottesting.mappers;

import com.dev.springboottesting.dto.UserDto;
import com.dev.springboottesting.entity.User;

public class UserDTOConverter {

    public User converter(UserDto userDto){
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }


}
