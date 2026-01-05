package com.webapp.taskmanagement.controller;

import com.webapp.taskmanagement.dto.AuthResponse;
import com.webapp.taskmanagement.dto.LoginRequest;
import com.webapp.taskmanagement.dto.SignupRequest;
import com.webapp.taskmanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    private ResponseEntity<String> signup(
            @RequestBody SignupRequest signupRequest) throws Exception {
        return ResponseEntity.ok(authService.signupUser(signupRequest));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest loginRequest) throws Exception {
        return ResponseEntity.ok(authService.loginUser(loginRequest));
    }

}
