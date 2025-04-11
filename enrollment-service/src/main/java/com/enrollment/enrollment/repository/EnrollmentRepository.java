package com.enrollment.enrollment.repository;

import com.enrollment.enrollment.model.Enrollment;
import com.enrollment.enrollment.model.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findByStudentId(Integer studentId);
    List<Enrollment> findByStudentIdAndStatus(Integer studentId, EnrollmentStatus status);
    Optional<Enrollment> findByStudentIdAndCourseId(Integer studentId, Integer courseId);
} 