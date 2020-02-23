package org.example.repository;

import org.example.domain.model.TaskModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository extends PagingAndSortingRepository<TaskModel, String> {
}
