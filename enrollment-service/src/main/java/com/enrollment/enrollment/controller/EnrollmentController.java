package com.enrollment.enrollment.controller;

import com.enrollment.enrollment.model.Enrollment;
import com.enrollment.enrollment.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudentId(studentId));
    }

    @GetMapping("/student/{studentId}/active")
    public ResponseEntity<List<Enrollment>> getActiveEnrollmentsByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentService.getActiveEnrollmentsByStudentId(studentId));
    }

    @PostMapping
    public ResponseEntity<Enrollment> enrollCourse(@RequestBody Enrollment enrollment) {
        try {
            return ResponseEntity.ok(enrollmentService.enrollCourse(enrollment));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/student/{studentId}/course/{courseId}/drop")
    public ResponseEntity<Enrollment> dropCourse(
            @PathVariable Long studentId,
            @PathVariable Integer courseId) {
        try {
            return ResponseEntity.ok(enrollmentService.dropCourse(studentId, courseId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 