package com.dev.springboottesting.repository;

import com.dev.springboottesting.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        //add the user to the database before calling test method
        User user = User.builder()
                .id(9L)
                .firstName("Jasmine")
                .lastName("Mary")
                .email("jasmine@mary.com")
                .password("Axaskm.@97")
                .build();

        entityManager.persist(user);
    }

    @Test
    public void TestFindUserByIdInRepository(){
        User user = userRepository.findUserById(9L);
        assertEquals(user.getId(), 9L);
    }


}