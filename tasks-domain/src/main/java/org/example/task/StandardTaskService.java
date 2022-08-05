package org.example.task;

import com.google.common.collect.Lists;
import io.jsondb.InvalidJsonDbApiUsageException;
import org.example.domain.model.TaskModel;
import org.example.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class StandardTaskService implements TaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StandardTaskService.class);

    private TaskRepository repository;

    public StandardTaskService() {
        // default constructor for spring
    }

    @Autowired
    public StandardTaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TaskModel> findAllTasks() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public TaskModel createTask(TaskModel task) {
        return repository.save(task);
    }

    @Override
    public void deleteTask(String id) {
        if (id != null) {
            // first get the entity by id, because
            // the jsondb has an error in the implementation
            // at the function 'deleteById(String)', while this code developed...
            Optional<TaskModel> entity = repository.findById(id);
            if (entity.isPresent()) {
                repository.delete(entity.get());
            }
        }
    }

    @Override
    public TaskModel updateTask(TaskModel entity) {
        if (entity != null) {
            try {
                repository.delete(entity);
            } catch (InvalidJsonDbApiUsageException e) {
                LOGGER.error(e.getMessage(), e);
                // ignore: task not exists...
            }
            return repository.save(entity);
        } else {
            return null;
        }
    }

    @Override
    public Optional<TaskModel> getTask(String id) {
        return repository.findById(id);
    }
}
