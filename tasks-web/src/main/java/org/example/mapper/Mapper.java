package org.example.web.formdata.mapper;

import org.example.domain.model.TaskModel;
import org.example.web.formdata.TaskDTO;

import java.util.List;

/**
 * Convert DTO/JSON to Model and otherwise.
 */
public interface Mapper {
    /**
     * Convert a TaskModel to TakDTO.
     *
     * @param model - the TaskModel
     * @return the taskDTO
     */
    TaskDTO toDTO(TaskModel model);

    /**
     * Convert a list of TaskModel to a list of TaskDTO.
     *
     * @param models - the list of TaskModel
     * @return the list of TaskDTO
     */
    List<TaskDTO> toDTOs(List<TaskModel> models);

    /**
     * Convert a TaskDTO to TaskModel.
     *
     * @param dto
     * @return
     */
    TaskModel toModel(TaskDTO dto);

    /**
     * Convert a list of TaskModel to a list of TaskDTO.
     *
     * @param dtos - a list of TaskDTO's
     * @return a list of TaskModel
     */
    List<TaskModel> toModels(List<TaskDTO> dtos);

    /**
     * Create a TaskModel with a specific id.
     *
     * @param id - the id of the TaskModel
     * @return a new TaskModel with the id.
     */
    TaskModel fromId(String id);

    /**
     * Convert a Object to String
     *
     * @param obj - the object that should be converted to a json string.
     * @return a json string of an object
     */
    String toJson(Object obj);

    /**
     * Convert a json string to a specific Object.
     *
     * @param json  - the json string
     * @param clazz - the class to which the json should converted
     * @param <T>   - the type of the class to which sould converted
     * @return a object of the json string
     */
    <T> T fromJson(String json, Class<T> clazz);
}
