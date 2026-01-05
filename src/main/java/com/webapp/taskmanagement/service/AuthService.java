package com.webapp.taskmanagement.service;

import com.webapp.taskmanagement.dto.AuthResponse;
import com.webapp.taskmanagement.dto.LoginRequest;
import com.webapp.taskmanagement.dto.SignupRequest;

public interface AuthService {

    String signupUser(SignupRequest signupRequest) throws Exception;

    AuthResponse loginUser(LoginRequest loginRequest);
}
