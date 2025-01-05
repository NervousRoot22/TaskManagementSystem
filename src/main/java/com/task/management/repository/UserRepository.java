package com.task.management.repository;

import com.task.management.entity.RegisterUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<RegisterUserEntity, String> {
}
