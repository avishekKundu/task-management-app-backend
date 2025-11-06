package com.webapp.taskmanagement.entity;

import com.webapp.taskmanagement.enumeration.Priority;
import com.webapp.taskmanagement.enumeration.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
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

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
    }
}
