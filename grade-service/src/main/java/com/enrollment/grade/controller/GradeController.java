package com.enrollment.grade.controller;

import com.enrollment.grade.model.Grade;
import com.enrollment.grade.service.GradeService;
import com.enrollment.grade.dto.GpaResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Grade>> getStudentGrades(@PathVariable Integer studentId) {
        return ResponseEntity.ok(gradeService.getStudentGrades(studentId));
    }

    @GetMapping("/student/{studentId}/term")
    public ResponseEntity<List<Grade>> getTermGrades(
            @PathVariable Integer studentId,
            @RequestParam String schoolYear,
            @RequestParam Integer term) {
        return ResponseEntity.ok(gradeService.getTermGrades(studentId, schoolYear, term));
    }

    @PostMapping
    public ResponseEntity<Grade> submitGrade(@RequestBody Grade grade) {
        try {
            return ResponseEntity.ok(gradeService.submitGrade(grade));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Grade grade) {
        try {
            return ResponseEntity.ok(gradeService.updateGrade(id, grade));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/student/{studentId}/gpa/term")
    public ResponseEntity<GpaResult> calculateTermGpa(
            @PathVariable Integer studentId,
            @RequestParam String schoolYear,
            @RequestParam Integer term) {
        return ResponseEntity.ok(gradeService.calculateTermGpa(studentId, schoolYear, term));
    }

    @GetMapping("/student/{studentId}/gpa/cumulative")
    public ResponseEntity<GpaResult> calculateCumulativeGpa(@PathVariable Integer studentId) {
        return ResponseEntity.ok(gradeService.calculateCumulativeGpa(studentId));
    }
}