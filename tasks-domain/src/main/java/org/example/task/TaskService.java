package org.example.task;

import org.example.domain.model.TaskModel;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    void deleteTask(String id);

    List<TaskModel> findAllTasks();

    TaskModel createTask(TaskModel task);

    TaskModel updateTask(TaskModel toModel);

    Optional<TaskModel> getTask(String id);
}
