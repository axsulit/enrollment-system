package com.enrollment.grade.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "grades", schema = "grade")
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "school_year", nullable = false)
    private String schoolYear;  // e.g., "A.Y. 2024-2025"

    @Column(name = "term", nullable = false)
    private Integer term;  // 1, 2, or 3

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "faculty_id")
    private Long facultyId;

    @Column(name = "grade_value", nullable = false)
    @Enumerated(EnumType.STRING)
    private GradeValue gradeValue;

    @Column(name = "units", nullable = false)
    private BigDecimal units;

    @Column(name = "remarks")
    private String remarks;

    public enum GradeValue {
        FOUR_POINT_ZERO("4.00"),
        THREE_POINT_FIVE("3.50"),
        THREE_POINT_ZERO("3.00"),
        TWO_POINT_FIVE("2.50"),
        TWO_POINT_ZERO("2.00"),
        ONE_POINT_FIVE("1.50"),
        ONE_POINT_ZERO("1.00"),
        ZERO("0.00"),
        PASSED("P"),
        NO_GRADE("NGS");

        private final String value;

        GradeValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public BigDecimal getNumericValue() {
            try {
                return new BigDecimal(value);
            } catch (NumberFormatException e) {
                return null;  // For P and NGS
            }
        }
    }
} 