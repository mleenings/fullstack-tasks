package org.example.repository;

import com.google.common.annotations.VisibleForTesting;
import io.jsondb.InvalidJsonDbApiUsageException;
import io.jsondb.JsonDBTemplate;
import io.jsondb.crypto.Default1Cipher;
import io.jsondb.crypto.ICipher;
import org.example.domain.model.TaskModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class StandardTaskRepository implements TaskRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(StandardTaskRepository.class);
    private static final String DB_PATH = "db";
    private static final String ENCRYPTION_KEY = "1r8+24pibarAWgS85/Heeg==";
    private static final String NOT_SUPPORTED = "This method is not supported!";
    private JsonDBTemplate jsonDBTemplate;
    @Value("${db.path}")
    private String dbPath;

    public StandardTaskRepository() {
        // default constructor
    }

    @PostConstruct
    public void init() {
        final String pojoPackageName = TaskModel.class.getPackageName();
        ICipher cipher = null;
        try {
            cipher = new Default1Cipher(ENCRYPTION_KEY);
        } catch (GeneralSecurityException e) {
            // ignored: cipher is optional...
            LOGGER.error(e.getMessage(), e);
        }

        jsonDBTemplate = new JsonDBTemplate(dBFilesLocation(), pojoPackageName, cipher);
        if (!jsonDBTemplate.collectionExists(TaskModel.class)) {
            jsonDBTemplate.createCollection(TaskModel.class);
        }
    }

    @VisibleForTesting
    public String dBFilesLocation() {
        String db;
        if (dbPath == null) {
            db = DB_PATH;
        } else {
            db = dbPath;
        }
        return db;
    }

    @VisibleForTesting
    public void setJsonDBTemplate(JsonDBTemplate jsonDBTemplate) {
        this.jsonDBTemplate = jsonDBTemplate;
    }


    @Override
    public Iterable findAll(Sort sort) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public Page findAll(Pageable pageable) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public TaskModel save(TaskModel entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
        jsonDBTemplate.insert(entity);
        return entity;
    }

    @Override
    public Iterable saveAll(Iterable entities) {
        List<TaskModel> saved = new LinkedList<>();
        for (Object obj : entities) {
            if (obj instanceof TaskModel) {
                saved.add(save((TaskModel) obj));
            }
        }
        return saved;
    }

    @Override
    public Optional<TaskModel> findById(String id) {
        TaskModel entity = jsonDBTemplate.findById(id, TaskModel.class);
        if (entity == null) {
            return Optional.empty();
        } else {
            return Optional.of(entity);
        }
    }

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    @Override
    public Iterable<TaskModel> findAll() {
        try {
            return jsonDBTemplate.findAll(TaskModel.class);
        } catch (InvalidJsonDbApiUsageException e) {
            // not found
            return new ArrayList<>();
        }
    }

    @Override
    public Iterable<TaskModel> findAllById(Iterable iterable) {
        List<TaskModel> tasks = new ArrayList<>();
        if (iterable != null) {
            for (Object obj : iterable) {
                if (obj instanceof String) {
                    Optional<TaskModel> task = findById((String) obj);
                    if (task.isPresent()) {
                        tasks.add(task.get());
                    }
                }
            }
        }
        return tasks;
    }

    @Override
    public long count() {
        return StreamSupport.stream(findAll().spliterator(), false).count();
    }

    @Override
    public void deleteById(String id) {
        Optional<TaskModel> toDelte = findById(id);
        toDelte.ifPresent(taskModel -> jsonDBTemplate.remove(taskModel, TaskModel.class));
    }

    @Override
    public void delete(@NotNull TaskModel entity) {
        if (entity != null) {
            try {
                jsonDBTemplate.remove(entity, entity.getClass());
            } catch (InvalidJsonDbApiUsageException e) {
                // Already deleted: ignore
            }
        }
    }

    @Override
    public void deleteAll(@NotNull Iterable entities) {
        if (entities != null) {
            entities.forEach(
                    e -> {
                        if (e instanceof TaskModel) {
                            delete((TaskModel) e);
                        }
                    });
        }
    }

    @Override
    public void deleteAll() {
        StreamSupport.stream((findAll()).spliterator(), false).forEach(this::delete);
    }
}
