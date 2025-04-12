package com.enrollment.grade.service;

import com.enrollment.grade.model.Grade;
import com.enrollment.grade.repository.GradeRepository;
import com.enrollment.grade.dto.GpaResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;

    public List<Grade> getStudentGrades(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public List<Grade> getTermGrades(Long studentId, String schoolYear, Integer term) {
        return gradeRepository.findByStudentIdAndSchoolYearAndTerm(studentId, schoolYear, term);
    }

    @Transactional
    public Grade submitGrade(Grade grade) {
        validateGrade(grade);
        return gradeRepository.save(grade);
    }

    @Transactional
    public Grade updateGrade(Long id, Grade gradeDetails) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        validateGrade(gradeDetails);
        
        grade.setGradeValue(gradeDetails.getGradeValue());
        grade.setRemarks(gradeDetails.getRemarks());
        
        return gradeRepository.save(grade);
    }

    public GpaResult calculateTermGpa(Long studentId, String schoolYear, Integer term) {
        List<Grade> grades = gradeRepository.findGradesForTermGpaCalculation(studentId, schoolYear, term);
        return calculateGpa(grades);
    }

    public GpaResult calculateCumulativeGpa(Long studentId) {
        List<Grade> grades = gradeRepository.findGradesForGpaCalculation(studentId);
        return calculateGpa(grades);
    }

    // Faculty grade management methods
    public List<String> getAvailableSchoolYears() {
        return gradeRepository.findDistinctSchoolYears();
    }

    public List<Long> getFacultyCourses(String schoolYear, Integer term) {
        return gradeRepository.findDistinctCourseIdsBySchoolYearAndTerm(schoolYear, term);
    }

    public List<Grade> getCourseGrades(Long courseId, String schoolYear, Integer term) {
        return gradeRepository.findByCourseIdAndSchoolYearAndTerm(courseId, schoolYear, term);
    }

    public List<Grade> getFacultyGrades(Long facultyId) {
        return gradeRepository.findByFacultyId(facultyId);
    }

    @Transactional
    public List<Grade> submitBulkGrades(List<Grade> grades) {
        grades.forEach(this::validateGrade);
        return gradeRepository.saveAll(grades);
    }

    private GpaResult calculateGpa(List<Grade> grades) {
        BigDecimal totalUnits = BigDecimal.ZERO;
        BigDecimal totalGradePoints = BigDecimal.ZERO;

        for (Grade grade : grades) {
            if (grade.getGradeValue() != null) {
                String gradeValue = grade.getGradeValue().getValue();
                try {
                    BigDecimal numericGrade = new BigDecimal(gradeValue);
                    totalUnits = totalUnits.add(grade.getUnits());
                    totalGradePoints = totalGradePoints.add(numericGrade.multiply(grade.getUnits()));
                } catch (NumberFormatException e) {
                    // Skip non-numeric grades (P, NGS)
                    continue;
                }
            }
        }

        if (totalUnits.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal gpa = totalGradePoints.divide(totalUnits, 2, RoundingMode.HALF_UP);
            return new GpaResult(gpa, totalUnits);
        }

        return new GpaResult(BigDecimal.ZERO, BigDecimal.ZERO);
    }

    private void validateGrade(Grade grade) {
        if (grade.getTerm() < 1 || grade.getTerm() > 3) {
            throw new IllegalArgumentException("Term must be between 1 and 3");
        }
        
        if (grade.getUnits().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Units must be greater than 0");
        }
        
        if (grade.getGradeValue() == null) {
            throw new IllegalArgumentException("Grade value cannot be null");
        }

        if (grade.getCourseId() == null) {
            throw new IllegalArgumentException("Course ID cannot be null");
        }

        if (grade.getStudentId() == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
    }
} 