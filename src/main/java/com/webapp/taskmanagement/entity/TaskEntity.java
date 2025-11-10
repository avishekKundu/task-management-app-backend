package com.webapp.taskmanagement.entity;

import com.webapp.taskmanagement.dto.TaskDTO;
import com.webapp.taskmanagement.enumeration.Priority;
import com.webapp.taskmanagement.enumeration.Status;
import com.webapp.taskmanagement.util.ROIUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "taskInfo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2048)
    private String notes;

    @Column(precision = 19, scale = 4)
    private BigDecimal revenue;

    @Column(precision = 19, scale = 4)
    private BigDecimal timeTaken;

    @Column(precision = 19, scale = 4)
    private BigDecimal roi;

    private Priority priority;

    private Status status;

    private Instant createdAt;

    @Version
    private Long version;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
    }

    public static TaskEntity fromDto(TaskDTO dto) {
        TaskEntity e = TaskEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .notes(dto.getNotes())
                .revenue(dto.getRevenue())
                .timeTaken(dto.getTimeTaken())
                .priority(dto.getPriority() == null ? Priority.MEDIUM : dto.getPriority())
                .status(dto.getStatus() == null ? Status.TODO : dto.getStatus())
                .build();
        e.setRoi(ROIUtils.computeROI(e.getRevenue(), e.getTimeTaken()));
        return e;
    }
}
