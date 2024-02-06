package ua.foxminded.javaspring.consoleMenu.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.javaspring.consoleMenu.ItemInstance;
import ua.foxminded.javaspring.consoleMenu.TestData;
import ua.foxminded.javaspring.consoleMenu.dao.CourseDAO;
import ua.foxminded.javaspring.consoleMenu.dao.GroupDAO;
import ua.foxminded.javaspring.consoleMenu.dao.StudentAtCourseDAO;
import ua.foxminded.javaspring.consoleMenu.dao.StudentDAO;
import ua.foxminded.javaspring.consoleMenu.exception.InvalidIdException;
import ua.foxminded.javaspring.consoleMenu.model.Course;
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

    private Student student;
    private Course course;

    private TestData testData = new TestData();
    private ItemInstance itemInstance = new ItemInstance();

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        student = new Student(testData.studentFirstName, testData.studentLastName, itemInstance.getGroup());
        course = itemInstance.getCourse();
    }

    @Test
    void addNewStudent_shouldReturnTrue_whenIsAddedSuccessfully() {
        when(groupDAO.existsById(anyLong())).thenReturn(true);
        when(studentDAO.save(any(Student.class))).thenReturn(itemInstance.getStudent());

        assertTrue(studentService.addNewStudent(student));

        verify(groupDAO).existsById(student.getGroup().getId());
        verify(studentDAO).save(student);
    }

    @Test
    void addNewStudent_shouldReturnFalse_whenReceivedGroupIdIsNotExist() {
        when(groupDAO.existsById(anyLong())).thenReturn(false);

        assertThrows(InvalidIdException.class, () -> studentService.addNewStudent(student));

        verify(groupDAO).existsById(student.getGroup().getId());
    }

    @Test
    void getAllCoursesOfStudent_shouldReturnListOfAllStudentCourses_whenIsCalled() {
        List<StudentAtCourse> coursesOfStudent = new ArrayList<>();
        coursesOfStudent.add(new StudentAtCourse(student, new Course(1L)));
        coursesOfStudent.add(new StudentAtCourse(student, new Course(2L)));
        coursesOfStudent.add(new StudentAtCourse(student, new Course(3L)));

        when(studentAtCourseDAO.findAllStudentCourses(any(Student.class))).thenReturn(coursesOfStudent);

        assertThat(studentService.getAllCoursesOfStudent(student)).isSameAs(coursesOfStudent);

        verify(studentAtCourseDAO).findAllStudentCourses(student);
    }

    @Test
    void deleteStudent_shouldReturnTrue_whenDeletedSuccessfully() {
        studentService.deleteStudent(student);

        verify(studentDAO).delete(student);
    }

    @Test
    void getItemByID_shouldReturnStudent_whenIsExist() {
        Student student = itemInstance.getStudent();

        when(studentDAO.findById(anyLong())).thenReturn(Optional.of(student));

        assertThat(studentService.getStudent(student)).isEqualTo(student);

        verify(studentDAO).findById(student.getId());    }

    @Test
    void addStudentToCourse_shouldReturnTrue_whenStudentIsAddedAtCourse() {
        StudentAtCourse studentAtCourse = new StudentAtCourse(student, course);

        when(courseDAO.existsById(anyLong())).thenReturn(true);
        when(studentAtCourseDAO.save(studentAtCourse)).thenReturn(new StudentAtCourse(1L, student, course));

        assertTrue(studentService.addStudentToCourse(studentAtCourse));

        verify(courseDAO).existsById(course.getId());
        verify(studentAtCourseDAO).save(studentAtCourse);
    }

    @Test
    void addStudentToCourse_shouldReturnTrue_whenCourseIdIsNotExist() {
        StudentAtCourse studentAtCourse = new StudentAtCourse(student, course);

        when(courseDAO.existsById(anyLong())).thenReturn(false);

        assertThrows(InvalidIdException.class, () -> studentService.addStudentToCourse(studentAtCourse));

        verify(courseDAO).existsById(course.getId());
    }

    @Test
    void removeStudentFromCourse_shouldReturnTrue_whenIsRemoved() {
        StudentAtCourse studentToRemove = new StudentAtCourse(1L, student);

        List<StudentAtCourse> studentAtCourses = new ArrayList<>();
        studentAtCourses.add(new StudentAtCourse(1L, student));
        studentAtCourses.add(new StudentAtCourse(2L, student));
        studentAtCourses.add(new StudentAtCourse(3L, student));

        when(studentAtCourseDAO.findAllStudentCourses(any(Student.class))).thenReturn(studentAtCourses);

        studentService.removeStudentFromCourse(studentToRemove);

        verify(studentAtCourseDAO).findAllStudentCourses(studentToRemove.getStudent());
        verify(studentAtCourseDAO).delete(studentToRemove);
    }

    @Test
    void removeStudentFromCourse_shouldReturnFalse_whenEnrollmentNotExistOrNotRelateToStudent() {
        StudentAtCourse studentToRemove = new StudentAtCourse(10L, student);

        List<StudentAtCourse> studentAtCourses = new ArrayList<>();
        studentAtCourses.add(new StudentAtCourse(1L, student));
        studentAtCourses.add(new StudentAtCourse(2L, student));
        studentAtCourses.add(new StudentAtCourse(3L, student));

        when(studentAtCourseDAO.findAllStudentCourses(any(Student.class))).thenReturn(studentAtCourses);

        assertThrows(InvalidIdException.class, () -> studentService.removeStudentFromCourse(studentToRemove));

        verify(studentAtCourseDAO).findAllStudentCourses(studentToRemove.getStudent());
    }
}
