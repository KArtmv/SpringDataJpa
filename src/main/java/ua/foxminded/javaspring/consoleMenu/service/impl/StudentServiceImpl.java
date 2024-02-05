package ua.foxminded.javaspring.consoleMenu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.consoleMenu.dao.CourseDAO;
import ua.foxminded.javaspring.consoleMenu.dao.GroupDAO;
import ua.foxminded.javaspring.consoleMenu.dao.StudentAtCourseDAO;
import ua.foxminded.javaspring.consoleMenu.dao.StudentDAO;
import ua.foxminded.javaspring.consoleMenu.exception.InvalidIdException;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.service.StudentService;

import java.util.List;
import java.util.Objects;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentDAO studentDAO;
    private StudentAtCourseDAO studentAtCourseDAO;
    private GroupDAO groupDAO;
    private CourseDAO courseDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO, StudentAtCourseDAO studentAtCourseDAO, GroupDAO groupDAO, CourseDAO courseDAO) {
        this.studentDAO = studentDAO;
        this.studentAtCourseDAO = studentAtCourseDAO;
        this.groupDAO = groupDAO;
        this.courseDAO = courseDAO;
    }

    @Override
    public Student addNewStudent(Student student) {
        if (groupDAO.existsById(student.getGroup().getId())) {
            return studentDAO.save(student);
        } else {
            throw new InvalidIdException("Not found group with received ID: " + student.getGroup().getId());
        }
    }

    @Override
    public void deleteStudent(Student student) {
        studentDAO.delete(student);
    }

    @Override
    public StudentAtCourse addStudentToCourse(StudentAtCourse studentAtCourse) {
        if (courseDAO.existsById(studentAtCourse.getCourse().getId())) {
            return studentAtCourseDAO.save(studentAtCourse);
        } else {
            throw new InvalidIdException("Not found course with received ID: " + studentAtCourse.getCourse().getId());
        }
    }

    @Override
    public void removeStudentFromCourse(StudentAtCourse studentAtCourse) {
        if (getAllCoursesOfStudent(studentAtCourse.getStudent()).stream()
                .anyMatch(id -> Objects.equals(id.getId(), studentAtCourse.getId()))) {
            studentAtCourseDAO.delete(studentAtCourse);
        } else {
            throw new InvalidIdException(String.format(
                    "Received enrollment ID: %s, is not found or not relate to given student: ID: %s ",
                    studentAtCourse.getId(), studentAtCourse.getStudent().getId()));
        }
    }

    @Override
    public Student getStudent(Student student) {
        return studentDAO.findById(student.getId()).orElseThrow(() ->
                new InvalidIdException("Not found student with given ID: " + student.getId()));
    }

    @Override
    public List<StudentAtCourse> getAllCoursesOfStudent(Student student) {
        return studentAtCourseDAO.findAllStudentCourses(student);
    }
}
