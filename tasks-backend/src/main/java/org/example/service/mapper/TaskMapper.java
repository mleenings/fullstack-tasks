package org.example.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.model.TaskModel;
import org.example.service.dto.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskMapper implements Mapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskMapper.class);

    @Override
    public TaskDTO toDTO(TaskModel model) {
        return new TaskDTO(model);
    }

    @Override
    public List<TaskDTO> toDTOs(List<TaskModel> models) {
        return models.stream().filter(Objects::nonNull).map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public TaskModel toModel(TaskDTO dto) {
        TaskModel model;
        if (dto == null) {
            model = null;
        } else {
            model = new TaskModel();
            model.setId(dto.getId());
            model.setText(dto.getText());
            model.setDone(dto.isDone());
        }
        return model;
    }

    @Override
    public List<TaskModel> toModels(List<TaskDTO> dtos) {
        return dtos.stream().filter(Objects::nonNull).map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public TaskModel fromId(String id) {
        TaskModel model = null;
        if (id != null) {
            model = new TaskModel();
            model.setId(id);
        }
        return model;
    }

    @Override
    public String toJson(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
