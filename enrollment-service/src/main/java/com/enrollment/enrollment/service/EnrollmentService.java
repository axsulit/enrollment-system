package com.enrollment.enrollment.service;

import com.enrollment.enrollment.model.Enrollment;
import com.enrollment.enrollment.model.EnrollmentStatus;
import com.enrollment.enrollment.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${course.service.url:http://course-service:8082}")
    private String courseServiceUrl;

    public List<Enrollment> getEnrollmentsByStudentId(Integer studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> getActiveEnrollmentsByStudentId(Integer studentId) {
        return enrollmentRepository.findByStudentIdAndStatus(studentId, EnrollmentStatus.ENROLLED);
    }

    @Transactional
    public Enrollment enrollCourse(Enrollment enrollment) {
        // Create a new enrollment record
        Enrollment newEnrollment = new Enrollment();
        newEnrollment.setStudentId(enrollment.getStudentId());
        newEnrollment.setCourseId(enrollment.getCourseId());
        newEnrollment.setCourseCode(enrollment.getCourseCode());
        newEnrollment.setCourseName(enrollment.getCourseName());
        newEnrollment.setScheduleDays(enrollment.getScheduleDays());
        newEnrollment.setProfessorId(enrollment.getProfessorId());
        newEnrollment.setStatus(EnrollmentStatus.ENROLLED);
        newEnrollment.setEnrolledAt(LocalDateTime.now());
        
        return enrollmentRepository.save(newEnrollment);
    }

    @Transactional
    public Enrollment dropCourse(Integer studentId, Integer courseId) {
        // Find any enrollment for this student and course
        List<Enrollment> enrollments = enrollmentRepository.findByStudentIdAndCourseIdOrderByIdDesc(studentId, courseId);
        
        if (enrollments.isEmpty()) {
            throw new RuntimeException("Enrollment not found");
        }
        
        // Get the most recent enrollment
        Enrollment enrollment = enrollments.get(0);
        
        // Only decrement course capacity if the enrollment was in ENROLLED state
        if (enrollment.getStatus() == EnrollmentStatus.ENROLLED) {
            try {
                // Call course service to decrement the course capacity
                restTemplate.postForEntity(
                    courseServiceUrl + "/courses/" + courseId + "/drop",
                    null,
                    Void.class
                );
            } catch (Exception e) {
                throw new RuntimeException("Failed to update course capacity: " + e.getMessage());
            }
        }
        
        // Update the status and timestamps
        enrollment.setStatus(EnrollmentStatus.DROPPED);
        enrollment.setDroppedAt(LocalDateTime.now());
        enrollment.setUpdatedAt(LocalDateTime.now());
        
        return enrollmentRepository.save(enrollment);
    }
} 