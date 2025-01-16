package com.rasimalimgulov.reportapi.service;

import com.rasimalimgulov.reportapi.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUserName(String username);
}
