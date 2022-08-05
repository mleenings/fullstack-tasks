package org.example.web.task;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.example.task.TaskModel;
import org.example.task.TaskService;
import org.example.util.HeaderUtil;
import org.example.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller for Task.
 * <p>
 * Swagger Api Documentation:
 *
 * @see <a href="http://localhost:8080/swagger-ui.html">http://localhost:8080/swagger-ui.html</a>
 */
@RestController
@RequestMapping(value = "/api")
@Api("Tasks Swagger API Documentation")
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
     * @param TaskVM the task to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new task
     * @throws URISyntaxException uriException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tasks/")
    @ApiOperation("Creates a new task")
    public ResponseEntity<TaskVM> ceateTask(@Valid @RequestBody TaskVM TaskVM)
            throws URISyntaxException {
        LOGGER.debug("REST request POST : /api/tasks/{}", TaskVM);
        try {
            if (TaskVM.getId() != null) {
                return ResponseEntity.badRequest().build();
            } else {
                TaskVM newTask = taskMapper.toViewModel(taskService.createTask(taskMapper.toModel(TaskVM)));
                return ResponseEntity.created(new URI("/api/tasks/" + newTask.getId()))
                        .headers(
                                HeaderUtil.createAlert(
                                        applicationName, "userManagement.created", String.valueOf(newTask.getId())))
                        .body(newTask);
            }
        } catch (Throwable t) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Could not created", t);
        }
    }

    /**
     * {@code PUT /api/tasks} : Updates an existing Task.
     *
     * @param TaskVM the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated task.
     */
    @PutMapping("/tasks/")
    @ApiOperation("Updates an existing task")
    public ResponseEntity<TaskVM> updateTask(@Valid @RequestBody TaskVM TaskVM) {
        LOGGER.debug("REST request PUT /tasks/{}", TaskVM);
        taskService.updateTask(taskMapper.toModel(TaskVM));
        return ResponseEntity.ok().build();
    }

    /**
     * {@code GET /api/tasks} : get all tasks. <br>
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all tasks.
     */
    @GetMapping("/tasks/")
    @ApiOperation("Returns all existing tasks")
    public ResponseEntity<List<TaskVM>> getAllTasks(Pageable pageable) {
        LOGGER.debug("REST request GET: /api/tasks/");
        try {
            final Page<TaskVM> page = convert(taskService.findAllTasks(), pageable);
            HttpHeaders headers =
                    PaginationUtil.generatePaginationHttpHeaders(
                            ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } catch (Throwable t) {
            LOGGER.error(t.getMessage(), t);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Could not find all tasks", t);
        }
    }

    /**
     * {@code GET /api/tasks/:id} : get a task with a specific id.
     *
     * @param id the id of the task to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the task to the
     * id, or with status {@code 404 (Not Found)}.
     */
	@GetMapping("/tasks/{id}")
    @ApiOperation("Returns a specific task")
    public ResponseEntity<TaskVM> getTask(@PathVariable String id) throws URISyntaxException {
        LOGGER.debug("REST request GET: /api/tasks/{}", id);
        try {
            Optional<TaskModel> taskModel = taskService.getTask(id);
            if (taskModel.isPresent()) {
                TaskVM task = taskMapper.toViewModel(taskModel.get());
                return ResponseEntity.created(new URI("/api/tasks/" + task.getId()))
                        .headers(
                                HeaderUtil.createAlert(
                                        applicationName, "userManagement.created", String.valueOf(task.getId())))
                        .body(task);
            } else {
            	return new ResponseEntity<TaskVM>(HttpStatus.NOT_FOUND);
            }
        } catch (Throwable t) {
            LOGGER.error(t.getMessage(), t);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Task Not Found", t);
        }
    }

    /**
     * {@code DELETE /api/tasks/:id} : delete a specific Task.
     *
     * @param id the id of the task to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tasks/{id}")
    @ApiOperation("Deletes a specific task")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        LOGGER.debug("REST request Delete: /api/tasks/{}", id);
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok().build();
        } catch (Throwable t) {
            LOGGER.error(t.getMessage(), t);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete", t);
        }
    }

    /**
     * Convert a list of {@link TaskModel} to a Page<TaskModel>
     *
     * @param tasks    - a list of TaskModels
     * @param pageable - the pagination information
     * @return a page, a sublist of a list of objects
     */
    private Page<TaskVM> convert(List<TaskModel> tasks, Pageable pageable) {
        int start = Math.toIntExact(pageable.getOffset());
        int end = Math.toIntExact(Math.min((start + pageable.getPageSize()), tasks.size()));
        return new PageImpl<>(
                tasks.subList(start, end).stream()
                		.map(m -> taskMapper.toViewModel(m))
                		.collect(Collectors.toList()),
                pageable,
                tasks.size());
    }
}
