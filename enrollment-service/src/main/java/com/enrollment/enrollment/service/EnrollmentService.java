package com.enrollment.enrollment.service;

import com.enrollment.enrollment.model.Enrollment;
import com.enrollment.enrollment.model.EnrollmentStatus;
import com.enrollment.enrollment.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public List<Enrollment> getEnrollmentsByStudentId(Integer studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> getActiveEnrollmentsByStudentId(Integer studentId) {
        return enrollmentRepository.findByStudentIdAndStatus(studentId, EnrollmentStatus.ENROLLED);
    }

    @Transactional
    public Enrollment enrollCourse(Enrollment enrollment) {
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        enrollment.setEnrolledAt(LocalDateTime.now());
        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    public Enrollment dropCourse(Integer studentId, Integer courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollment.setStatus(EnrollmentStatus.DROPPED);
        enrollment.setUpdatedAt(LocalDateTime.now());
        return enrollmentRepository.save(enrollment);
    }
} 