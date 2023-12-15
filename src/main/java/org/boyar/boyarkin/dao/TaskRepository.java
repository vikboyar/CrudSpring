package org.boyar.boyarkin.dao;

import org.boyar.boyarkin.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> getTaskByIdAndCreatorId(Long id, Long creatorId);
}
