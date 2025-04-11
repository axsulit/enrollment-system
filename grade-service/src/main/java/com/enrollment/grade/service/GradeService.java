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

    public List<Grade> getStudentGrades(Integer studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public List<Grade> getTermGrades(Integer studentId, String schoolYear, Integer term) {
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

    public GpaResult calculateTermGpa(Integer studentId, String schoolYear, Integer term) {
        List<Grade> grades = gradeRepository.findGradesForTermGpaCalculation(studentId, schoolYear, term);
        return calculateGpa(grades);
    }

    public GpaResult calculateCumulativeGpa(Integer studentId) {
        List<Grade> grades = gradeRepository.findGradesForGpaCalculation(studentId);
        return calculateGpa(grades);
    }

    private GpaResult calculateGpa(List<Grade> grades) {
        BigDecimal totalUnits = BigDecimal.ZERO;
        BigDecimal totalGradePoints = BigDecimal.ZERO;

        for (Grade grade : grades) {
            BigDecimal numericValue = grade.getGradeValue().getNumericValue();
            if (numericValue != null) {  // Skip P and NGS grades
                totalUnits = totalUnits.add(grade.getUnits());
                totalGradePoints = totalGradePoints.add(numericValue.multiply(grade.getUnits()));
            }
        }

        BigDecimal gpa = totalUnits.compareTo(BigDecimal.ZERO) > 0 
            ? totalGradePoints.divide(totalUnits, 2, RoundingMode.HALF_UP)
            : BigDecimal.ZERO;

        return new GpaResult(gpa, totalUnits);
    }

    private void validateGrade(Grade grade) {
        if (grade.getTerm() < 1 || grade.getTerm() > 3) {
            throw new IllegalArgumentException("Term must be between 1 and 3");
        }
        
        if (grade.getUnits().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Units must be greater than 0");
        }
        
        // Add more validations as needed
    }
} 