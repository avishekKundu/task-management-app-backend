package com.webapp.taskmanagement.service;

import com.sun.jdi.request.DuplicateRequestException;
import com.webapp.taskmanagement.config.JWTUtil;
import com.webapp.taskmanagement.dto.AuthResponse;
import com.webapp.taskmanagement.dto.LoginRequest;
import com.webapp.taskmanagement.dto.SignupRequest;
import com.webapp.taskmanagement.entity.User;
import com.webapp.taskmanagement.enumeration.Role;
import com.webapp.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public String signupUser(SignupRequest signupRequest) throws Exception {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new DuplicateRequestException("Username already exists");
        }
        User user = new User();
        user.setName(signupRequest.getName());
        user.setAddress(signupRequest.getAddress());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setMobileNumber(signupRequest.getMobileNumber());
        user.setRole(Role.USER);
        userRepository.save(user);
        var token = jwtUtil.generateToken(user);
        return "User Registered Successfully";
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                ));

        var user = userRepository.findByEmail(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var token = jwtUtil.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
