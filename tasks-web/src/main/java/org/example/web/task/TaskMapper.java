package org.example.web.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.web.Mapper;
import org.example.task.TaskModel;
import org.example.util.CustomSerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskMapper implements Mapper<TaskModel, TaskVM> {
  
  @Override
  public TaskModel toModel(TaskVM vm) {
    final TaskModel model;
    if(vm == null) {
    	model = new TaskModel();
    } else {
    	model = TaskModel.builder()
    			.id(vm.getId())
    			.text(vm.getText())
    			.done(vm.isDone())
    			.build();
    }
    return model;
  }

  @Override
  public List<TaskModel> toModelList(List<TaskVM> models) {
    return models.stream().filter(Objects::nonNull).map(this::toModel).collect(Collectors.toList());
  }

  @Override
  public TaskVM toViewModel(TaskModel model) {
	  TaskVM vm;
    if (model == null) {
      vm = null;
    } else {
      vm = TaskVM.builder()
  			.id(model.getId())
  			.text(model.getText())
  			.done(model.isDone())
  			.build();
    }
    return vm;
  }

  @Override
  public List<TaskVM> toViewModelList(List<TaskModel> models) {
    return models.stream().filter(Objects::nonNull).map(this::toViewModel).collect(Collectors.toList());
  }
  
  @Override
  public TaskModel[] toModelArray(TaskVM[] models) {
	    return Arrays.asList(models).stream()
	            .filter(Objects::nonNull)
	            .map(this::toModel)
	            .collect(Collectors.toList())
	            .toArray(new TaskModel[models.length]);
  }

  @Override
  public TaskVM[] toViewModelArray(TaskModel[] tos) {
	    return Arrays.asList(tos).stream()
	            .filter(Objects::nonNull)
	            .map(this::toViewModel)
	            .collect(Collectors.toList())
	            .toArray(new TaskVM[tos.length]);
  }

  @Override
  public String toJson(Object obj) throws JsonProcessingException {
    ObjectMapper om = new ObjectMapper();
    om.setSerializerProvider(new CustomSerializerProvider());
    return om.writeValueAsString(obj);
  }

  @Override
  public <T> T fromJson(String json, Class<T> clazz) {
    T obj = null;
    try {
      obj = new ObjectMapper().readValue(json, clazz);
    } catch (JsonMappingException e) {
      log.error(e.getMessage(), e);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    } catch (IOException e) {
    	log.error(e.getMessage(), e);
	}
    return obj;
  }
}
