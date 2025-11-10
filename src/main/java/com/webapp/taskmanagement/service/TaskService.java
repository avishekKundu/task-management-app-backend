package com.webapp.taskmanagement.service;

import com.webapp.taskmanagement.dto.TaskDTO;
import com.webapp.taskmanagement.entity.TaskEntity;
import com.webapp.taskmanagement.exception.ResourceNotFoundException;
import com.webapp.taskmanagement.repository.TaskRepository;
import com.webapp.taskmanagement.util.ROIUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskDTO> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(TaskDTO::toDto)
                .collect(Collectors.toList());
    }

    public TaskDTO create(TaskDTO taskDTO) {
        TaskEntity taskEntity = TaskEntity.fromDto(taskDTO);
        taskEntity.setId(null);
        taskEntity.setVersion(taskDTO.getVersion() + 1);
        TaskEntity savedTaskEntity = taskRepository.save(taskEntity);
        return TaskDTO.toDto(savedTaskEntity);
    }

    public TaskDTO update(Long id, TaskDTO taskDTO) {
        try {
            TaskEntity existingTask = taskRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setNotes(taskDTO.getNotes());
            existingTask.setRevenue(taskDTO.getRevenue());
            existingTask.setTimeTaken(taskDTO.getTimeTaken());
            existingTask.setPriority(taskDTO.getPriority());
            existingTask.setStatus(taskDTO.getStatus());
            existingTask.setRoi(ROIUtils
                    .computeROI(existingTask.getRevenue(), existingTask.getTimeTaken()));
            TaskEntity updateTask = taskRepository.save(existingTask);
            return TaskDTO.toDto(updateTask);
        } catch (StaleObjectStateException e) {
            throw new RuntimeException("Task was modified or deleted by another user. Please refresh.", e);
        }
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found: " + id);
        }
        taskRepository.deleteById(id);
    }

    public TaskDTO findById(Long id) {
        return taskRepository.findById(id)
                .map(TaskDTO::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));
    }

}
