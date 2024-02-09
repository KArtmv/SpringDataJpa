package ua.foxminded.javaspring.consoleMenu.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.consoleMenu.dao.CourseDAO;
import ua.foxminded.javaspring.consoleMenu.dao.GroupDAO;
import ua.foxminded.javaspring.consoleMenu.dao.StudentDAO;
import ua.foxminded.javaspring.consoleMenu.exception.InvalidIdException;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);


    private StudentDAO studentDAO;
    private GroupDAO groupDAO;
    private CourseDAO courseDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO, GroupDAO groupDAO, CourseDAO courseDAO) {
        this.studentDAO = studentDAO;
        this.groupDAO = groupDAO;
        this.courseDAO = courseDAO;
    }

    @Override
    public boolean addNewStudent(Student student) {
        if (groupDAO.existsById(student.getGroup().getId())) {
            return studentDAO.save(student).getId() != null;
        } else {
            throw new InvalidIdException("Not found group with received ID: " + student.getGroup().getId());
        }
    }

    @Override
    public void deleteStudent(Student student) {
        studentDAO.delete(student);
    }

    @Override
    public boolean addStudentToCourse(Student student, Course course) {
        Course filalCourse = courseDAO.findById(course.getId()).orElseThrow(() ->
                new InvalidIdException("Not found course with received ID: " + course.getId()));

            if (student.addCourse(filalCourse)) {
                return studentDAO.save(student).getId() != null;
            } else {
                LOGGER.error("Add course id: {} to student id: {} is filed.", student.getId(), course.getId());
            }
        return false;
    }


    @Override
    public boolean removeStudentFromCourse(Student student, Course course) {
        if (student.removeCourse(courseDAO.findById(course.getId()).orElseThrow(() ->
                new InvalidIdException(String.format("Received course id: %s, is not found.", course.getId()))))) {
            return studentDAO.save(student).getId() != null;
        } else {
            LOGGER.info("Course with given id: {} is not relate to student id: {}", course.getId(), student.getId());
            return false;
        }
    }

    @Override
    public Student getStudent(Student student) {
        return studentDAO.findById(student.getId()).orElseThrow(() ->
                new InvalidIdException("Not found student with given ID: " + student.getId()));
    }
}
