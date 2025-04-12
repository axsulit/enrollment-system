package com.enrollment.enrollment.repository;

import com.enrollment.enrollment.model.Enrollment;
import com.enrollment.enrollment.model.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findByStudentIdAndStatus(Long studentId, EnrollmentStatus status);
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Integer courseId);
    List<Enrollment> findByStudentIdAndCourseIdOrderByIdDesc(Long studentId, Integer courseId);
} 