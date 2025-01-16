package com.stefanini.repositories;

import com.stefanini.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
    Optional<TaskModel> findByTitle(String title);
}
