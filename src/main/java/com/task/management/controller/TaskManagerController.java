package com.task.management.controller;

import com.task.management.entity.TaskEntity;
import com.task.management.entity.UserEntity;
import com.task.management.repository.TaskRepository;
import com.task.management.repository.UserRepository;
import com.task.management.service.JwtUtilService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskManagerController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtilService jwtUtilService;

    @PostMapping("/tasks/create")
    public ResponseEntity<String> createTask(@Valid @RequestBody TaskEntity task,
                                             @RequestHeader("Authorization") String token) {
        final String email = jwtUtilService.extractEmail(token.substring(7));  // Remove 'Bearer ' prefix
        final UserEntity user = userRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        taskRepository.save(task);
        if(null != user.getTasks()) {
            user.getTasks().add(task);
        } else {
            user.setTasks(List.of(task));
        }
        userRepository.save(user);
        return ResponseEntity.ok("Task created successfully.");
    }

    @PostMapping("/tasks/update")
    public ResponseEntity<String> updateTask(@Valid @RequestBody TaskEntity task,
                                             @RequestHeader("Authorization") String token) {
        final TaskEntity taskEntityFound = taskRepository.findById(task.getTaskId().toString())
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskEntityFound.setDescriptionOfTask(null != task.getDescriptionOfTask() ?
                task.getDescriptionOfTask() : taskEntityFound.getDescriptionOfTask());
        taskEntityFound.setTaskName(null != task.getTaskName() ?
                task.getTaskName() : taskEntityFound.getTaskName());
        taskEntityFound.setPriority(0 != task.getPriority() ?
                task.getPriority() : taskEntityFound.getPriority());
        taskEntityFound.setStatus(null != task.getStatus() ?
                task.getStatus() : taskEntityFound.getStatus());
        taskEntityFound.setUser(task.getUser());
        taskRepository.save(taskEntityFound);
        return ResponseEntity.ok("Task updated successfully.");
    }

    @GetMapping("/tasks/details")
    public List<TaskEntity> displayTaskDetailsBasedOnUserEmail(@RequestHeader("Authorization")
                                                                         String token) {
        final String email = jwtUtilService.extractEmail(token.substring(7));  // Remove 'Bearer ' prefix
        final UserEntity user = userRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getTasks();
    }

    @GetMapping("/tasks/delete")
    public ResponseEntity<String> deleteTask(final Long taskIdIToBeDeleted,
                                       @RequestHeader("Authorization") String token) {
        final String email = jwtUtilService.extractEmail(token.substring(7));  // Remove 'Bearer ' prefix
        final UserEntity user = userRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        taskRepository.deleteById(taskIdIToBeDeleted.toString());
        final List<TaskEntity> taskEntityList = new ArrayList<>();
        for(TaskEntity task : user.getTasks()) {
            if (taskIdIToBeDeleted != task.getTaskId()) {
                taskEntityList.add(task);
            }
        }
        user.setTasks(taskEntityList);
        userRepository.save(user);
        return ResponseEntity.ok("Task deleted successfully.");
    }
}