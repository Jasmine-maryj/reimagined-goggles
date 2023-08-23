package com.dev.userservice.service;

import com.dev.userservice.entity.User;
import com.dev.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(9L)
                .firstName("abcd")
                .lastName("efgh")
                .email("kidau.dfg45@ksnd.com")
                .password("Axaskm.@97")
                .build();

        Mockito.when(userRepository.findUserById(9L)).thenReturn(user);
    }

    @Test
    public void TestFindUserById(){
        Long id = Long.valueOf(9);
        User user = userService.findUserById(id);
        assertEquals(id, user.getId());
    }


}