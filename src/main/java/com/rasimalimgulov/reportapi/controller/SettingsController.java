package com.rasimalimgulov.reportapi.controller;

import com.rasimalimgulov.reportapi.requests.ChangeLoginRequest;
import com.rasimalimgulov.reportapi.requests.ChangePasswordRequest;
import com.rasimalimgulov.reportapi.service.UserServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class SettingsController {
    private final UserServiceImpl userService;

    public SettingsController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/change-login")
    public ResponseEntity<Void> changeLogin(@RequestBody ChangeLoginRequest changeLoginRequest) {
        userService.changeLogin(changeLoginRequest.getUsername(), changeLoginRequest.getNewUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest.getUsername(), changePasswordRequest.getPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getRoles(@RequestParam String username) {
        return ResponseEntity.ok(userService.getRoles(username));
    }
}
