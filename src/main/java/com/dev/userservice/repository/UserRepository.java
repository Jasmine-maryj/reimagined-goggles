package com.dev.userservice.repository;

import com.dev.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long userId);

    User findUserByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User findByFirstName(String firstName);
}
