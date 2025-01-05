package com.task.management.controller;

import com.task.management.entity.LoginUserEntity;
import com.task.management.entity.Role;
import com.task.management.entity.RegisterUserEntity;
import com.task.management.repository.UserRepository;
import com.task.management.service.JwtUtilService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtilService jwtUtilService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/auth/register")
    public ResponseEntity<String> getUserRegister(@Valid @RequestBody RegisterUserEntity user, Error error) {
        if(null == error) {
            if (user != null && userRepository.existsById(user.getUserName())) {
                return ResponseEntity.badRequest().body("User already exists.");
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                return ResponseEntity.ok("User registered.");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid Input");
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> getLogin(@RequestBody LoginUserEntity user) {
        Optional<RegisterUserEntity> optionalUser = userRepository.findById(user.getUserName());
        if (optionalUser.isPresent()) {
            RegisterUserEntity storedUser = optionalUser.get();
            if (passwordEncoder.matches(user.getPassword(), storedUser.getPassword())) {
                String token = jwtUtilService.generateToken(user.getUserName());
                return ResponseEntity.ok("Access Granted. Token: " + token);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
    }

    @GetMapping("/hello")
    public void getHello() {

    }
}
