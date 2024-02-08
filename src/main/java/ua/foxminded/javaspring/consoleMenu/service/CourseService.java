package ua.foxminded.javaspring.consoleMenu.service;

import ua.foxminded.javaspring.consoleMenu.model.Course;

import java.util.List;

public interface CourseService {
    Course getCourse(Course course);

    List<Course> getAllCourses();
}
