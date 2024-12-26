package com.task.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class UserEntity {
    @Id
    @Column(name="userName")
    @NotNull
    private String userName;
    @Email
    @Column(name="email")
    private String email;
    @Size(min=8)
    @Column(name="password")
    private String password;
    @Column(name="role")
    private Role role;
}
