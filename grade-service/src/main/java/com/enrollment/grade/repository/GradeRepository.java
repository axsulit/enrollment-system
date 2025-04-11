package com.enrollment.grade.repository;

import com.enrollment.grade.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Integer studentId);
    
    List<Grade> findByStudentIdAndSchoolYearAndTerm(Integer studentId, String schoolYear, Integer term);
    
    @Query("SELECT g FROM Grade g WHERE g.studentId = :studentId AND g.gradeValue != 'PASSED' AND g.gradeValue != 'NO_GRADE'")
    List<Grade> findGradesForGpaCalculation(@Param("studentId") Integer studentId);
    
    @Query("SELECT g FROM Grade g WHERE g.studentId = :studentId AND g.schoolYear = :schoolYear AND g.term = :term AND g.gradeValue != 'PASSED' AND g.gradeValue != 'NO_GRADE'")
    List<Grade> findGradesForTermGpaCalculation(
        @Param("studentId") Integer studentId,
        @Param("schoolYear") String schoolYear,
        @Param("term") Integer term
    );
} 