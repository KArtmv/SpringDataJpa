package ua.foxminded.javaspring.consoleMenu.dao;

import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import java.util.List;

public interface StudentAtCourseDAO extends DAO<StudentAtCourse, Long> {
    List<StudentAtCourse> allStudentsFromCourse(Course course);

    void removeStudentFromAllTheirCourses(Student student);

    boolean removeStudentFromCourse(StudentAtCourse studentAtCourse);
}
