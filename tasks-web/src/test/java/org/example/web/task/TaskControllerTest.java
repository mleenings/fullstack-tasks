package org.example.web.controller;

import org.example.Application;
import org.example.domain.model.TaskModel;
import org.example.repository.StandardTaskRepository;
import org.example.web.formdata.TaskDTO;
import org.example.web.formdata.mapper.Mapper;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class})
@ContextConfiguration
@WebAppConfiguration
@ActiveProfiles("test")
public class TaskControllerTest {
    private static final String REQUEST_SUFFIX = "/tasks/";
    private static final String REQUEST_PREFIX = "/api";
    private static final String REQUEST_URI = REQUEST_PREFIX + REQUEST_SUFFIX;
    private static MockMvc mvc;
    @Autowired
    private StandardTaskRepository repository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private Mapper mapper;

    @After
    public void after() {
        cleanUp();
    }

    private void cleanUp() {
        repository.deleteAll();
        final File db = new File(repository.dBFilesLocation());
        if (db.exists()) {
            db.delete();
        }
    }

    @BeforeEach
    public void beforeEach() {
        cleanUp();
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createTaskTest() throws Exception {
        TaskModel expectedTask = createTask();
        String inputJson = mapper.toJson(expectedTask);
        MvcResult mvcResult =
                mvc.perform(
                        MockMvcRequestBuilders.post(REQUEST_URI)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(inputJson))
                        .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        TaskDTO actualTask = mapper.fromJson(content, TaskDTO.class);
        actualTask.setId(null);
        Assertions.assertEquals(new TaskDTO(expectedTask), actualTask);
    }

    @Test
    public void getTasksTest() throws Exception {
        final String text = "Hello";
        List<TaskModel> expected = Arrays.asList(createTask(text, 1), createTask(text, 2));
        repository.saveAll(expected);
        MvcResult mvcResult =
                mvc.perform(
                        MockMvcRequestBuilders.get(REQUEST_URI).accept(MediaType.APPLICATION_JSON_VALUE))
                        .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        TaskDTO[] tasks = mapper.fromJson(content, TaskDTO[].class);
        Assertions.assertEquals(expected.size(), tasks.length);
        Assertions.assertEquals(createTask(text, 1).getText(), tasks[0].getText());
    }

    @Test
    public void getSpecificTaskTest() throws Exception {
        List<TaskModel> expected = Arrays.asList(createTask(), createTask());
        List<TaskModel> saved = (List<TaskModel>) repository.saveAll(expected);
        String uri = REQUEST_URI + saved.get(0).getId();
        MvcResult mvcResult =
                mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
                        .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        TaskDTO task = mapper.fromJson(content, TaskDTO.class);
        Assertions.assertEquals(task.getId(), saved.get(0).getId());
    }

    @Test
    public void updateTaskTest() throws Exception {
        String text = "newText";
        TaskModel task = createTask(3);
        repository.save(task);
        task.setText(text);
        String inputJson = mapper.toJson(task);
        MvcResult mvcResult =
                mvc.perform(
                        MockMvcRequestBuilders.put(REQUEST_URI)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(inputJson))
                        .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }

    @Test
    public void deleteTaskTest() throws Exception {
        TaskModel task = createTask(4);
        repository.save(task);
        String uri = REQUEST_URI + task.getId();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertFalse(repository.existsById(task.getId()));
    }

    private TaskModel createTask() {
        return createTask("Task", null);
    }

    private TaskModel createTask(Integer id) {
        return createTask("Task-", id);
    }

    private TaskModel createTask(String name, Integer id) {
        TaskModel task = new TaskModel();
        task.setText(id == null ? name : name + id);
        task.setId(id == null ? null : String.valueOf(id));
        return task;
    }

    private void assertEquals(TaskDTO dto, TaskModel model) {
        assertEquals(model, dto);
    }

    private void assertEquals(TaskModel model, TaskDTO dto) {
        Assertions.assertEquals(model.toString(), dto.toString());
    }
}
