package com.stefanini.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.stefanini.enums.TaskStatus;
import com.stefanini.models.TaskModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    public interface TaskView {
        public static interface TaskPost {}
        public static interface TaskPut {}
    }

    @JsonView(TaskView.TaskPut.class)
    @NotNull(message = "ID cannot be null", groups = TaskView.TaskPut.class)
    private UUID taskId;

    @NotBlank(message = "Title cannot be null", groups = {TaskView.TaskPost.class, TaskView.TaskPut.class})
    @JsonView({TaskView.TaskPost.class, TaskView.TaskPut.class})
    private String title;

    @NotBlank(message = "Description cannot be null", groups = {TaskView.TaskPost.class, TaskView.TaskPut.class})
    @JsonView({TaskView.TaskPost.class, TaskView.TaskPut.class})
    private String description;

    @NotNull(message = "Status cannot be null", groups = {TaskView.TaskPost.class, TaskView.TaskPut.class})
    @JsonView({TaskView.TaskPost.class, TaskView.TaskPut.class})
    private TaskStatus status;

    public TaskDto(TaskModel taskSaved) {
        BeanUtils.copyProperties(taskSaved, this);
        this.status = taskSaved.getStatus();
    }

}
