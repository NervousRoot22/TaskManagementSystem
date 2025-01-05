package com.task.management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TaskEntity {
    @Id
    @Column(name="taskId")
    private Long taskId;
    @Column(name="taskName")
    private String taskName;
    @Column(name="descriptionOfTask")
    private String descriptionOfTask;
    @Column(name="priority")
    private int priority;
    @Column(name="status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "userName")  // FK references userName field
    private UserEntity user;
}
