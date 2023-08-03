package com.dev.springboottesting.repository;

import com.dev.springboottesting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long userId);

    User findUserByEmail(String email);
}
