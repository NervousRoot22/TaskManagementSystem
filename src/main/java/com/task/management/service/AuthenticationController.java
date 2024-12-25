package com.task.management.service;

import com.task.management.entity.Role;
import com.task.management.entity.UserEntity;
import com.task.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

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
        userRepository.save(user);


    }
}
