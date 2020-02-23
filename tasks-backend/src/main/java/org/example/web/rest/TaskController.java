package org.example.web.rest;

import org.example.domain.model.TaskModel;
import org.example.service.TaskService;
import org.example.service.dto.TaskDTO;
import org.example.service.mapper.TaskMapper;
import org.example.web.rest.util.HeaderUtil;
import org.example.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class TaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Value("${tasks.clientApp.name}")
    private String applicationName;

    private TaskService taskService;
    private TaskMapper taskMapper;

    public TaskController() {
        // default Constructor
    }

    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    /**
     * {@code POST /api/tasks} : Creates a new task.
     *
     * @param taskDTO the task to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new task
     * @throws URISyntaxException uriException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tasks/")
    public ResponseEntity<TaskDTO> ceateTask(@Valid @RequestBody TaskDTO taskDTO)
            throws URISyntaxException {
        LOGGER.debug("REST request POST : /api/tasks/{}", taskDTO);
        if (taskDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        } else {
            TaskDTO newTask = taskMapper.toDTO(taskService.createTask(taskMapper.toModel(taskDTO)));
            return ResponseEntity.created(new URI("/api/tasks/" + newTask.getId()))
                    .headers(
                            HeaderUtil.createAlert(
                                    applicationName, "userManagement.created", String.valueOf(newTask.getId())))
                    .body(newTask);
        }
    }

    /**
     * {@code PUT /api/tasks} : Updates an existing Task.
     *
     * @param taskDTO the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated task.
     */
    @PutMapping("/tasks/")
    public ResponseEntity<TaskDTO> updateTask(@Valid @RequestBody TaskDTO taskDTO) {
        LOGGER.debug("REST request PUT /tasks/{}", taskDTO);
        taskService.updateTask(taskMapper.toModel(taskDTO));
        return ResponseEntity.ok().build();
    }

    /**
     * {@code GET /api/tasks} : get all tasks. <br>
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all tasks.
     */
    @GetMapping("/tasks/")
    public ResponseEntity<List<TaskDTO>> getAllTasks(Pageable pageable) {
        LOGGER.debug("REST request GET: /api/tasks/");
        final Page<TaskDTO> page = convert(taskService.findAllTasks(), pageable);
        HttpHeaders headers =
                PaginationUtil.generatePaginationHttpHeaders(
                        ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /api/tasks/:id} : get a task with a specific id.
     *
     * @param id the id of the task to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the task to the
     * id, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable String id) throws URISyntaxException {
        LOGGER.debug("REST request GET: /api/tasks/{}", id);
        Optional<TaskModel> taskModel = taskService.getTask(id);
        if (taskModel.isPresent()) {
            TaskDTO dto = taskMapper.toDTO(taskModel.get());
            return ResponseEntity.created(new URI("/api/tasks/" + dto.getId()))
                    .headers(
                            HeaderUtil.createAlert(
                                    applicationName, "userManagement.created", String.valueOf(dto.getId())))
                    .body(dto);
        } else {
            return (ResponseEntity<TaskDTO>) ResponseEntity.notFound();
        }
    }

    /**
     * {@code DELETE /api/tasks/:id} : delete a specific Task.
     *
     * @param id the id of the task to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        LOGGER.debug("REST request Delete: /api/tasks/{}", id);
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Convert a list of {@link TaskModel} to a Page<TaskDTO>
     *
     * @param tasks    - a list of TaskModels
     * @param pageable - the pagination information
     * @return a page, a sublist of a list of objects
     */
    private Page<TaskDTO> convert(List<TaskModel> tasks, Pageable pageable) {
        int start = Math.toIntExact(pageable.getOffset());
        int end = Math.toIntExact(Math.min((start + pageable.getPageSize()), tasks.size()));
        return new PageImpl<>(
                tasks.subList(start, end).stream().map(TaskDTO::new).collect(Collectors.toList()),
                pageable,
                tasks.size());
    }
}
