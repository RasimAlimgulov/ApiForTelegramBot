package com.rasimalimgulov.reportapi.service;

import com.rasimalimgulov.reportapi.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByUserName(String username);
    User save(User user);
}
