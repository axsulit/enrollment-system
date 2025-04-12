package com.enrollment.grade.repository;

import com.enrollment.grade.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);
    
    List<Grade> findByStudentIdAndSchoolYearAndTerm(Long studentId, String schoolYear, Integer term);
    
    @Query("SELECT g FROM Grade g WHERE g.studentId = :studentId AND g.gradeValue NOT IN ('PASSED', 'NO_GRADE', 'NGS')")
    List<Grade> findGradesForGpaCalculation(@Param("studentId") Long studentId);
    
    @Query("SELECT g FROM Grade g WHERE g.studentId = :studentId AND g.schoolYear = :schoolYear AND g.term = :term AND g.gradeValue NOT IN ('PASSED', 'NO_GRADE', 'NGS')")
    List<Grade> findGradesForTermGpaCalculation(
        @Param("studentId") Long studentId,
        @Param("schoolYear") String schoolYear,
        @Param("term") Integer term
    );

    // Faculty grade management methods
    List<Grade> findByFacultyId(Long facultyId);
    
    List<Grade> findByCourseId(Long courseId);
    
    List<Grade> findByCourseIdAndSchoolYearAndTerm(Long courseId, String schoolYear, Integer term);
    
    @Query("SELECT DISTINCT g.schoolYear FROM Grade g ORDER BY g.schoolYear DESC")
    List<String> findDistinctSchoolYears();
    
    @Query("SELECT DISTINCT g.courseId FROM Grade g WHERE g.schoolYear = :schoolYear AND g.term = :term")
    List<Long> findDistinctCourseIdsBySchoolYearAndTerm(
        @Param("schoolYear") String schoolYear,
        @Param("term") Integer term
    );
} 