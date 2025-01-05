package com.task.management.repository;

import com.task.management.entity.TaskEntity;
import com.task.management.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, String> {
}
