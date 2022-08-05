package org.example.repository;

import io.jsondb.InvalidJsonDbApiUsageException;
import io.jsondb.JsonDBTemplate;
import org.example.task.TaskModel;
import org.example.task.JsonDbTaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
@RunWith(JUnitPlatform.class)
public class JsonDbTaskRepositoryTest {
    @Mock
    private JsonDBTemplate jsonDBTemplate;
    private JsonDbTaskRepository repository = new JsonDbTaskRepository();

    @BeforeEach
    public void init() {
        repository.setJsonDBTemplate(jsonDBTemplate);
    }

    @Test
    public void whenFindAllBySortThenThrowException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            repository.findAll(Sort.unsorted());
        });
    }

    @Test
    public void whenFindAllByPageableThenThrowException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            repository.findAll(Pageable.unpaged());
        });
    }

    @Test
    public void whenSaveWithNoIdThenSaveWithGeneratedId() {
        TaskModel task = mock(TaskModel.class);
        repository.save(task);
        verify(task).setId(any());
        verify(jsonDBTemplate).insert(task);
    }

    @Test
    public void whenSaveWithIdThenSaveWithOriginalId() {
        TaskModel task = mock(TaskModel.class);
        when(task.getId()).thenReturn("super Id");
        repository.save(task);
        verify(task, times(0)).setId(any());
        verify(jsonDBTemplate).insert(task);
    }

    @Test
    public void whenSaveAllThenSaveAll() {
        List<TaskModel> tasks = Arrays.asList(generateDummyTaskModel("1"), generateDummyTaskModel("2"));
        repository.saveAll(tasks);
        for (TaskModel task : tasks) {
            verify(jsonDBTemplate, times(1)).insert(task);
        }
    }

    @Test
    public void whenFindByIdAndFindOneThenReturnOne() {
        TaskModel expectedTask = generateDummyTaskModel();
        when(jsonDBTemplate.findById(expectedTask.getId(), TaskModel.class)).thenReturn(expectedTask);
        TaskModel actualTask = repository.findById(expectedTask.getId()).get();
        assertThat(actualTask).isEqualTo(expectedTask);
    }

    @Test
    public void whenFindByIdAndFoundNothingThenReturnEmpty() {
        TaskModel expectedTask = generateDummyTaskModel();
        when(jsonDBTemplate.findById(expectedTask.getId(), TaskModel.class)).thenReturn(null);
        Optional<TaskModel> actualTask = repository.findById(expectedTask.getId());
        assertThat(actualTask).isEqualTo(Optional.empty());
    }

    @Test
    public void whenExistsByIdAndExistsThenReturnTrue() {
        final String id = "1";
        when(jsonDBTemplate.findById(id, TaskModel.class)).thenReturn(null);
        final boolean actual = repository.existsById(id);
        assertThat(actual).isFalse();
    }

    @Test
    public void whenExistsByIdAndNotExistsThenReturnFalse() {
        final String id = "1";
        when(jsonDBTemplate.findById(id, TaskModel.class)).thenReturn(new TaskModel());
        final boolean actual = repository.existsById(id);
        assertThat(actual).isTrue();
    }

    @Test
    public void whenTasksExistsThenReturnAll() {
        List<TaskModel> expected = Arrays.asList(generateDummyTaskModel(), generateDummyTaskModel());
        when(jsonDBTemplate.findAll(TaskModel.class)).thenReturn(expected);
        Iterable<TaskModel> actual = repository.findAll();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenTasksNotExistsThenReturnEmptyList() {
        doThrow(InvalidJsonDbApiUsageException.class).when(jsonDBTemplate).findAll(TaskModel.class);
        Iterable<TaskModel> actual = repository.findAll();
        assertThat(actual).isEmpty();
    }

    @Test
    public void whenFindAllByIdAndNothingExitstsThenRetrunEmptyList() {
        List<String> ids = Arrays.asList("1");
        when(jsonDBTemplate.findById(ids.get(0), TaskModel.class)).thenReturn(null);
        Iterable<TaskModel> actual = repository.findAllById(ids);
        assertThat(actual).isEmpty();
    }

    @Test
    public void whenFindAllByIdThenRetrunAllById() {
        List<String> ids = Arrays.asList("1");
        TaskModel expected = generateDummyTaskModel(ids.get(0));
        when(jsonDBTemplate.findById(ids.get(0), TaskModel.class)).thenReturn(expected);
        Iterable<TaskModel> actual = repository.findAllById(ids);
        assertThat(actual).isEqualTo(Arrays.asList(expected));
    }

    @Test
    public void whenCountThenReturnSize() {
        List<TaskModel> expected = Arrays.asList(generateDummyTaskModel(), generateDummyTaskModel());
        when(jsonDBTemplate.findAll(TaskModel.class)).thenReturn(expected);
        long actual = repository.count();
        assertThat(actual).isEqualTo(Long.valueOf(expected.size()).longValue());
    }

    @Test
    public void whenDeleteByIdThenDelete() {
        final String id = "1";
        TaskModel expected = generateDummyTaskModel(id);
        when(jsonDBTemplate.findById(id, TaskModel.class)).thenReturn(expected);
        repository.deleteById(id);
        verify(jsonDBTemplate).remove(expected, TaskModel.class);
    }

    @Test
    public void whenDeleteAllSpecifiedThenDeleteAll() {
        List<TaskModel> expected = Arrays.asList(generateDummyTaskModel("1"), generateDummyTaskModel("2"));
        repository.deleteAll(expected);
        for (TaskModel task : expected) {
            verify(jsonDBTemplate).remove(task, TaskModel.class);
        }
    }

    @Test
    public void whenDeleteAllThenDeleteAll() {
        List<TaskModel> expected = Arrays.asList(generateDummyTaskModel("1"), generateDummyTaskModel("2"));
        when(jsonDBTemplate.findAll(TaskModel.class)).thenReturn(expected);
        repository.deleteAll();
        for (TaskModel task : expected) {
            verify(jsonDBTemplate).remove(task, TaskModel.class);
        }
    }

    @Test
    public void whenDelteTaskThenSuccess() {
        TaskModel task = generateDummyTaskModel();
        repository.delete(task);
        verify(jsonDBTemplate).remove(task, TaskModel.class);
    }

    @Test
    public void whenDelteNullThenDoNothing() {
        TaskModel task = generateDummyTaskModel();
        repository.delete(null);
        verify(jsonDBTemplate, times(0)).remove(task, TaskModel.class);
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
