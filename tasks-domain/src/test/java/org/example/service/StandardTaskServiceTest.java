package org.example.service;

import io.jsondb.InvalidJsonDbApiUsageException;
import org.example.task.TaskModel;
import org.example.task.JsonDbTaskRepository;
import org.example.task.TaskService;
import org.example.task.StandardTaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
@RunWith(JUnitPlatform.class)
public class StandardTaskServiceTest {
    @Mock
    private JsonDbTaskRepository repository;
    private TaskService service;

    @BeforeEach
    public void init() {
        service = new StandardTaskService(repository);
    }

    @Test
    public void whenCreateTaskThenReturnSavedTask() {
        TaskModel expectedTask = generateDummyTaskModel();
        when(repository.save(expectedTask)).thenReturn(expectedTask);
        TaskModel actualTask = service.createTask(expectedTask);
        assertThat(actualTask).isEqualTo(expectedTask);
    }

    @Test
    public void whenFindAllTasksThenReturnAllTasks() {
        List<TaskModel> expectedTasks = Arrays.asList(generateDummyTaskModel());
        when(repository.findAll()).thenReturn(expectedTasks);
        List<TaskModel> actualTasks = service.findAllTasks();
        assertThat(actualTasks).isEqualTo(expectedTasks);
    }

    @Test
    public void whenUpdateTaskThenUpdateTheTask() {
        TaskModel task = generateDummyTaskModel();
        service.updateTask(task);
        verify(repository, times(1)).delete(task);
        verify(repository, times(1)).save(task);
    }

    @Test
    public void whenTaskNotFoundAtUpdateThenSaveTheTask() {
        TaskModel task = generateDummyTaskModel();
        doThrow(InvalidJsonDbApiUsageException.class).when(repository).delete(task);
        service.updateTask(task);
        verify(repository, times(1)).save(task);
    }

    @Test
    public void whenGetTaskByIdThenReturnTheTask() {
        final String id = "1";
        TaskModel expectedTask = generateDummyTaskModel(id);
        when(repository.findById(id)).thenReturn(Optional.of(expectedTask));
        TaskModel actualTask = service.getTask(id).get();
        assertThat(actualTask).isEqualTo(expectedTask);
    }

    private TaskModel generateDummyTaskModel() {
        return generateDummyTaskModel("superId");
    }

    private TaskModel generateDummyTaskModel(String id) {
        TaskModel task = new TaskModel();
        task.setId(id);
        return task;
    }
}
