package com.task.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class UserEntity {
    @Id
    @Email
    @Column(name="email")
    private String email;
    @Column(name = "userName", unique = true)
    private String userName;
    @Size(min=8)
    @Column(name="password")
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TaskEntity> tasks;
}
