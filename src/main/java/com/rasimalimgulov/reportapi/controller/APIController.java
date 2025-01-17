package com.rasimalimgulov.reportapi.controller;

import com.rasimalimgulov.reportapi.entity.User;
import com.rasimalimgulov.reportapi.security.AuthRequest;
import com.rasimalimgulov.reportapi.security.JwtUtil;
import com.rasimalimgulov.reportapi.service.UserServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class APIController {
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public APIController(AuthenticationManager authenticationManager, UserServiceImpl userDetailsService, JwtUtil jwtUtil, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authentication")
    public String authentication(@RequestBody AuthRequest authRequest) throws Exception {

        log.info("Вызвался метод логирования");
        log.info(authRequest.getUsername());
        log.info(authRequest.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            log.info("Успешно прошла процедура authentication");
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        log.info("Получили UserDetails");
        final String jwt = jwtUtil.generateToken(userDetails);
        log.info("Сгенерировали токен");
        return jwt;
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws Exception {

        User user1 = userDetailsService.save(user);
        if (user1 != null) {
            return ResponseEntity.ok(user1);
        } else {
            return new ResponseEntity("Не получилось сохранить юзера", HttpStatus.BAD_REQUEST);
        }
    }
}
