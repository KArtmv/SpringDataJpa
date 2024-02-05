package ua.foxminded.javaspring.consoleMenu.service;

import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import java.util.List;

@Service
public interface StudentService {
    Student addNewStudent(Student student);

    void deleteStudent(Student student);

    StudentAtCourse addStudentToCourse(StudentAtCourse studentAtCourse);

    void removeStudentFromCourse(StudentAtCourse studentAtCourse);

    Student getStudent(Student student);

    List<StudentAtCourse> getAllCoursesOfStudent(Student student);
}
