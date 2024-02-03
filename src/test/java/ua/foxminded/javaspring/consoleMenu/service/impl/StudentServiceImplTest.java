package ua.foxminded.javaspring.consoleMenu.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.javaspring.consoleMenu.dao.CourseDAO;
import ua.foxminded.javaspring.consoleMenu.dao.GroupDAO;
import ua.foxminded.javaspring.consoleMenu.dao.StudentAtCourseDAO;
import ua.foxminded.javaspring.consoleMenu.dao.StudentDAO;
import ua.foxminded.javaspring.consoleMenu.exception.InvalidIdException;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    @Mock
    private StudentDAO studentDAO;
    @Mock
    private StudentAtCourseDAO studentAtCourseDAO;
    @Mock
    private GroupDAO groupDAO;
    @Mock
    private CourseDAO courseDAO;

    private Student student = new Student(1L, "firstName", "lastName", new Group(1L));
    private Course course = new Course(1L);


    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNewStudent_shouldReturnTrue_whenIsAddedSuccessfully() {
        when(groupDAO.getItemByID(anyLong())).thenReturn(Optional.of(student.getGroup()));
        when(studentDAO.addItem(any(Student.class))).thenReturn(true);

        assertTrue(studentService.addNewStudent(student));

        verify(groupDAO).getItemByID(student.getGroup().getId());
        verify(studentDAO).addItem(student);
    }

    @Test
    void addNewStudent_shouldReturnFalse_whenReceivedCourseIsNotExist() {
        when(groupDAO.getItemByID(anyLong())).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> studentService.addNewStudent(student));

        verify(groupDAO).getItemByID(student.getGroup().getId());
    }

    @Test
    void getAllCoursesOfStudent_shouldReturnListOfAllStudentCourses_whenIsCalled() {
        List<StudentAtCourse> coursesOfStudent = new ArrayList<>();
        coursesOfStudent.add(new StudentAtCourse(student, new Course(1L)));
        coursesOfStudent.add(new StudentAtCourse(student, new Course(2L)));
        coursesOfStudent.add(new StudentAtCourse(student, new Course(3L)));

        when(studentDAO.studentCourses(any(Student.class))).thenReturn(coursesOfStudent);

        assertThat(studentService.getAllCoursesOfStudent(student)).isSameAs(coursesOfStudent);

        verify(studentDAO).studentCourses(any(Student.class));
    }

    @Test
    void deleteStudent_shouldReturnTrue_whenDeletedSuccessfully() {
        when(studentDAO.removeStudent(any(Student.class))).thenReturn(true);

        assertThat(studentService.deleteStudent(student)).isTrue();

        verify(studentDAO).removeStudent(student);
    }

    @Test
    void getItemByID_shouldReturnStudent_whenIsExist() {
        when(studentDAO.getItemByID(anyLong())).thenReturn(Optional.of(student));

        assertThat(studentService.getStudent(student)).isEqualTo(student);

        verify(studentDAO).getItemByID(student.getId());
    }

    @Test
    void addStudentToCourse_shouldReturnTrue_whenStudentIsAddedAtCourse() {
        Course course = new Course(1L);
        StudentAtCourse studentAtCourse = new StudentAtCourse(student, course);

        when(courseDAO.getItemByID(anyLong())).thenReturn(Optional.of(course));
        when(studentAtCourseDAO.addItem(studentAtCourse)).thenReturn(true);

        assertTrue(studentService.addStudentToCourse(studentAtCourse));
    }

    @Test
    void addStudentToCourse_shouldReturnTrue_whenCourseIdIsNotExist() {
        StudentAtCourse studentAtCourse = new StudentAtCourse(student, course);

        when(courseDAO.getItemByID(anyLong())).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> studentService.addStudentToCourse(studentAtCourse));
    }

    @Test
    void removeStudentFromCourse_shouldReturnTrue_whenIsRemoved() {
        StudentAtCourse studentToRemove = new StudentAtCourse(1L, student);

        List<StudentAtCourse> studentAtCourses = new ArrayList<>();
        studentAtCourses.add(new StudentAtCourse(1L, student));
        studentAtCourses.add(new StudentAtCourse(2L, student));
        studentAtCourses.add(new StudentAtCourse(3L, student));

        when(studentDAO.studentCourses(any(Student.class))).thenReturn(studentAtCourses);
        when(studentAtCourseDAO.removeStudentFromCourse(studentToRemove)).thenReturn(true);

        assertTrue(studentService.removeStudentFromCourse(studentToRemove));

        verify(studentDAO).studentCourses(studentToRemove.getStudent());
        verify(studentAtCourseDAO).removeStudentFromCourse(studentToRemove);
    }

    @Test
    void removeStudentFromCourse_shouldReturnFalse_whenEnrollmentNotExistOrNotRelateToStudent() {
        StudentAtCourse studentToRemove = new StudentAtCourse(10L, student);

        List<StudentAtCourse> studentAtCourses = new ArrayList<>();
        studentAtCourses.add(new StudentAtCourse(1L, student));
        studentAtCourses.add(new StudentAtCourse(2L, student));
        studentAtCourses.add(new StudentAtCourse(3L, student));

        when(studentDAO.studentCourses(any(Student.class))).thenReturn(studentAtCourses);

        assertThrows(InvalidIdException.class, () -> studentService.removeStudentFromCourse(studentToRemove));

        verify(studentDAO).studentCourses(studentToRemove.getStudent());
    }
}
