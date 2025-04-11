package com.enrollment.course.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;
import java.util.Set;

@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer id;

    @Column(name = "course_code", unique = true, nullable = false, length = 10)
    private String courseCode;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @ElementCollection
    @CollectionTable(name = "course_schedule", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "day_of_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> scheduleDays;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "max_enrollees", nullable = false)
    private Integer maxEnrollees;

    @Column(name = "mode_of_learning", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private ModeOfLearning modeOfLearning;

    @Column(name = "professor_name", nullable = false)
    private String professorName;

    @Column(name = "current_enrollees", nullable = false)
    private Integer currentEnrollees = 0;

    public enum DayOfWeek {
        M, T, W, H, F, S
    }

    public enum ModeOfLearning {
        FOL, // Full Online
        HYB, // Hybrid
        F2F  // Face to Face
    }
} 