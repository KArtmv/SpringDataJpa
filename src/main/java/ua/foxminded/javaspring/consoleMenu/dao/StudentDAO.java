package ua.foxminded.javaspring.consoleMenu.dao;

import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import java.util.List;

public interface StudentDAO extends DAO<Student, Long> {
    List<StudentAtCourse> studentCourses(Student student);

    boolean removeStudent(Student student);
}
