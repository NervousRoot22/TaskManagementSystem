package com.task.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class LoginUserEntity {
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
}
