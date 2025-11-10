package com.webapp.taskmanagement.dto;

import com.webapp.taskmanagement.entity.TaskEntity;
import com.webapp.taskmanagement.enumeration.Priority;
import com.webapp.taskmanagement.enumeration.Status;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {

    private Long id;

    @NotBlank
    private String title;

    private String notes;

    @NotNull
    private BigDecimal revenue;

    @NotNull
    private BigDecimal timeTaken;

    private BigDecimal roi;

    private Priority priority;

    private Status status;

    private Instant createdAt;

    private Long version;

    public static TaskDTO toDto(TaskEntity e) {
        return TaskDTO.builder()
                .id(e.getId())
                .title(e.getTitle())
                .notes(e.getNotes())
                .revenue(e.getRevenue())
                .timeTaken(e.getTimeTaken())
                .roi(e.getRoi())
                .priority(e.getPriority())
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
                .version(e.getVersion())
                .build();
    }

}
