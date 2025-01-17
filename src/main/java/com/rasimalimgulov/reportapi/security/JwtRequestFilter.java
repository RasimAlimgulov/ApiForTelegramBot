package com.rasimalimgulov.reportapi.security;

import com.rasimalimgulov.reportapi.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    final private UserService userDetailsService;
    final private JwtUtil jwtUtil;

    public JwtRequestFilter(UserService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        log.info("Срабатывает jwt фильтр");
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        log.info("Получем jwt токен из заголовка");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            log.info("Юзернейм равен " + username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("Пытаемся получить UserDetails из userDetailsService.loadUserByUsername(username) ");
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); ///////////////
            log.info("В userDetails.getUserName = " + userDetails.getUsername());
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}