package ua.foxminded.javaspring.consoleMenu.service;

import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;

@Service
public interface StudentService {
    boolean addNewStudent(Student student);

    void deleteStudent(Student student);

    boolean addStudentToCourse(Student student, Course course);

    boolean removeStudentFromCourse(Student student, Course course);

    Student getStudent(Student student);

}
