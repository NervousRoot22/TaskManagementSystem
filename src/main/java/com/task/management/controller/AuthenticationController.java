package com.task.management.controller;

import com.task.management.entity.Role;
import com.task.management.entity.UserEntity;
import com.task.management.repository.UserRepository;
import com.task.management.service.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtilService jwtUtilService;

    @PostMapping("/auth/register")
    private ResponseEntity<String> getUserRegister(@PathVariable UserEntity user) {
        if(null!= user && userRepository.existsById(user.getUserName())) {
            return ResponseEntity.badRequest().build();
        } else {
            user.setRole(Role.USER);
            userRepository.save(user);
            return ResponseEntity.ok("User registered.");
        }
    }

    @PostMapping("/auth/login")
    private ResponseEntity<String> getLogin(@PathVariable UserEntity user) {
        if(userRepository.existsById(user.getUserName()) &&
                userRepository.findById(user.getUserName()).get().getPassword().equals(Optional.of(user.getPassword()))) {
            jwtUtilService.generateToken(user.getUserName());
            return ResponseEntity.ok("Access Granted");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
