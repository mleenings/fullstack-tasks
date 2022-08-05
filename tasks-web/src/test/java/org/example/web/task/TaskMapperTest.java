package org.example.web.formdata.mapper;

import org.example.domain.model.TaskModel;
import org.example.web.formdata.TaskDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskMapperTest {

    private Mapper mapper = new TaskMapper();

    @Test
    public void toDTOTest() {
        TaskModel taskModel = generateTaskModel();
        TaskDTO dto = mapper.toDTO(taskModel);
        assertEquals(dto, taskModel);
    }

    @Test
    public void toDTOsTest() {
        List<TaskModel> models = Arrays.asList(generateTaskModel(), generateTaskModel());
        List<TaskDTO> dtos = mapper.toDTOs(models);
        assertThat(models.size()).isEqualTo(dtos.size());
        assertEquals(dtos.get(0), models.get(0));
        assertEquals(dtos.get(1), models.get(1));
    }

    @Test
    public void toModelTest() {
        TaskDTO dto = generateTaskDto();
        TaskModel model = mapper.toModel(dto);
        assertEquals(dto, model);
    }

    @Test
    public void toModelsTest() {
        List<TaskDTO> dtos = Arrays.asList(generateTaskDto(), generateTaskDto());
        List<TaskModel> models = mapper.toModels(dtos);
        assertThat(models.size()).isEqualTo(dtos.size());
        assertEquals(dtos.get(0), models.get(0));
        assertEquals(dtos.get(1), models.get(1));
    }

    @Test
    public void fromIdTest() {
        final String id = "1";
        TaskModel task = mapper.fromId(id);
        assertThat(task.getId()).isEqualTo(id);
        assertThat(task.getText()).isNullOrEmpty();
        assertThat(task.isDone()).isFalse();
        assertThat(task.getPrivateKey()).isNullOrEmpty();
    }

    @Test
    public void fromJsonTest() {
        TaskDTO expected = generateTaskDto();
        TaskDTO actual = mapper.fromJson(expected.toString(), TaskDTO.class);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void toJsonTest() {
        TaskDTO expected = generateTaskDto();
        String actual = mapper.toJson(expected);
        assertThat(actual).isEqualTo(expected.toString());
    }

    private TaskModel generateTaskModel() {
        TaskModel task = new TaskModel();
        task.setId("superId");
        task.setText("super Text");
        task.setDone(true);
        task.setPrivateKey("privateKey");
        return task;
    }

    private TaskDTO generateTaskDto() {
        TaskDTO task = new TaskDTO();
        task.setId("superId");
        task.setText("super Text");
        task.setDone(true);
        return task;
    }

    private void assertEquals(TaskDTO dto, TaskModel model) {
        assertThat(dto.getId()).isEqualTo(dto.getId());
        assertThat(dto.getText()).isEqualTo(dto.getText());
        assertThat(dto.isDone()).isEqualTo(dto.isDone());
    }
}
