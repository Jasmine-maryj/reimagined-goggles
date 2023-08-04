package com.dev.springboottesting.controller;

import com.dev.springboottesting.dto.UserDto;
import com.dev.springboottesting.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDto user;

    @BeforeEach
    void setUp(){

    }

    @Test
    void addUser() {
        user = UserDto.builder()
                .firstName("Jasmine")
                .lastName("Mary")
                .email("jasmine@mary.com")
                .password("Axaskm.@97")
                .build();

    }

    @Test
    void findUserById() {

    }
}