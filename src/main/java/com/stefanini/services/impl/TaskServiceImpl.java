package com.stefanini.services.impl;

import com.stefanini.dtos.TaskDto;
import com.stefanini.exceptions.TaskException;
import com.stefanini.models.TaskModel;
import com.stefanini.repositories.TaskRepository;
import com.stefanini.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskDto save(TaskDto data) {
        Optional<TaskModel> taskModelOptional = this.taskRepository.findByTitle(data.getTitle());
        if (taskModelOptional.isPresent()) {
            throw new TaskException("A task with the same name cannot exist.");
        }
        TaskModel taskModel = new TaskModel(data);
        TaskModel taskSaved = this.taskRepository.save(taskModel);
        return new TaskDto(taskSaved);
    }

    @Override
    public Page<TaskModel> findAll(Pageable pageable) {
        return this.taskRepository.findAll(pageable);
    }

    @Override
    public TaskDto findById(UUID taskId) {
        try {
            TaskModel taskModel = this.taskRepository.getReferenceById(taskId);
            return new TaskDto(taskModel);
        } catch (Exception e) {
            throw new TaskException("Task with this id not found.");
        }
    }

    @Override
    public TaskDto update(TaskDto data) {
        Optional<TaskModel> taskModelOptional = this.taskRepository.findById(data.getTaskId());
        if (taskModelOptional.isEmpty()) {
            throw new TaskException("Task with this id not found.");
        }
        TaskModel taskModel = taskModelOptional.get();
        taskModel.setTitle(data.getTitle());
        taskModel.setDescription(data.getDescription());
        taskModel.setStatus(data.getStatus());
        this.taskRepository.save(taskModel);
        return new TaskDto(taskModel);
    }

    @Override
    public void delete(UUID taskId) {
        Optional<TaskModel> taskModelOptional = this.taskRepository.findById(taskId);
        if (taskModelOptional.isEmpty()) {
            throw new TaskException("Task with this id not found.");
        }
        this.taskRepository.delete(taskModelOptional.get());
    }

}
