package com.task.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserEntity {
    @Id
    private String userName;
    private String email;
    private String password;
    private Role role;
}
