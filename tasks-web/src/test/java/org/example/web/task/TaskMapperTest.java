package org.example.web.task;

import org.example.task.TaskModel;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskMapperTest {

    private TaskMapper mapper = new TaskMapper();

    @Test
    public void testToModel() {
        TaskVM viewModel = generateTaskViewModel();
        TaskModel model = mapper.toModel(viewModel);
        assertEquals(model, viewModel);
    }

    @Test
    public void toDTOsTest() {
        List<TaskVM> viewModels = Arrays.asList(generateTaskViewModel(), generateTaskViewModel());
        List<TaskModel> models = mapper.toModelList(viewModels);
        assertThat(viewModels.size()).isEqualTo(models.size());
        assertEquals(models.get(0), viewModels.get(0));
        assertEquals(models.get(1), viewModels.get(1));
    }

    @Test
	public void toViewModelTest() {
        TaskModel dto = generateTaskModel();
        TaskVM ViewModel = mapper.toViewModel(dto);
        assertEquals(dto, ViewModel);
    }

    @Test
    public void toViewModelsTest() {
        List<TaskModel> dtos = Arrays.asList(generateTaskModel(), generateTaskModel());
        List<TaskVM> ViewModels = mapper.toViewModelList(dtos);
        assertThat(ViewModels.size()).isEqualTo(dtos.size());
        assertEquals(dtos.get(0), ViewModels.get(0));
        assertEquals(dtos.get(1), ViewModels.get(1));
    }

	@Test
    public void fromJsonTest() {
        TaskVM expected = generateTaskViewModel();
        TaskVM actual = (TaskVM) mapper.fromJson(expected.toString(), TaskVM.class);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void toJsonTest() throws JsonProcessingException {
        TaskModel expected = generateTaskModel();
        String actual = mapper.toJson(expected);
        assertThat(actual).isEqualTo(expected.toString());
    }

    private TaskModel generateTaskModel() {
    	return TaskModel.builder()
    		.id("superId")
    		.text("super Text")
    		.done(true)
    		.privateKey("privateKey")
    		.build();
    }

    private TaskVM generateTaskViewModel() {
    	return TaskVM.builder()
        		.id("superId")
        		.text("super Text")
        		.done(true)
        		.build();
    }

    private void assertEquals(TaskModel dto, TaskVM ViewModel) {
        assertThat(dto.getId()).isEqualTo(dto.getId());
        assertThat(dto.getText()).isEqualTo(dto.getText());
        assertThat(dto.isDone()).isEqualTo(dto.isDone());
    }
}
