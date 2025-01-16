package com.stefanini.services;

import com.stefanini.dtos.TaskDto;
import com.stefanini.models.TaskModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TaskService {
    TaskDto save(TaskDto data);
    Page<TaskModel> findAll(Pageable pageable);
    TaskDto findById(UUID taskId);
    TaskDto update(TaskDto data);
    void delete(UUID taskId);
}
