package ua.foxminded.javaspring.consoleMenu.service;

import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import java.util.List;

public interface CourseService {
    List<StudentAtCourse> allStudentsFromCourse(Course course);

    List<Course> getAllCourses();
}
