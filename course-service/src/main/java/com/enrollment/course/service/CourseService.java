package com.enrollment.course.service;

import com.enrollment.course.model.Course;
import com.enrollment.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Integer id) {
        return courseRepository.findById(id);
    }

    public Optional<Course> getCourseByCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }

    @Transactional
    public Course createCourse(Course course) {
        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new RuntimeException("Course code already exists");
        }
        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Integer id, Course courseDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getCourseCode().equals(courseDetails.getCourseCode()) &&
            courseRepository.existsByCourseCode(courseDetails.getCourseCode())) {
            throw new RuntimeException("Course code already exists");
        }

        course.setCourseCode(courseDetails.getCourseCode());
        course.setCourseName(courseDetails.getCourseName());
        course.setScheduleDays(courseDetails.getScheduleDays());
        course.setStartTime(courseDetails.getStartTime());
        course.setEndTime(courseDetails.getEndTime());
        course.setMaxEnrollees(courseDetails.getMaxEnrollees());
        course.setModeOfLearning(courseDetails.getModeOfLearning());
        course.setProfessorName(courseDetails.getProfessorName());

        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Integer id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found");
        }
        courseRepository.deleteById(id);
    }

    @Transactional
    public void incrementEnrollees(Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        if (course.getCurrentEnrollees() >= course.getMaxEnrollees()) {
            throw new RuntimeException("Course is already full");
        }
        
        course.setCurrentEnrollees(course.getCurrentEnrollees() + 1);
        courseRepository.save(course);
    }

    @Transactional
    public void decrementEnrollees(Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        if (course.getCurrentEnrollees() <= 0) {
            throw new RuntimeException("No enrollees to remove");
        }
        
        course.setCurrentEnrollees(course.getCurrentEnrollees() - 1);
        courseRepository.save(course);
    }
} 