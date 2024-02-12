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
import ua.foxminded.javaspring.consoleMenu.dao.StudentDAO;
import ua.foxminded.javaspring.consoleMenu.exception.InvalidIdException;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    @Mock
    private StudentDAO studentDAO;
    @Mock
    private GroupDAO groupDAO;
    @Mock
    private CourseDAO courseDAO;

    private Student student;
    private Course course;
    private TestData testData = new TestData();
    private ItemInstance instance = new ItemInstance();

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        student = new Student(testData.studentFirstName, testData.studentLastName, instance.getGroup());
        course = instance.getCourse();
    }

    @Test
    void addNewStudent_shouldReturnTrue_whenIsAddedSuccessfully() {
        when(groupDAO.existsById(anyLong())).thenReturn(true);
        when(studentDAO.save(any(Student.class))).thenReturn(instance.getStudent());

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
    void deleteStudent_doNothing_whenDeletedSuccessfully() {
        studentService.deleteStudent(student);

        verify(studentDAO).delete(student);
    }

    @Test
    void getItemByID_shouldReturnStudent_whenIsExist() {
        when(studentDAO.findById(anyLong())).thenReturn(Optional.of(instance.getStudent()));

        assertThat(studentService.getStudent(instance.getStudentId())).isEqualTo(instance.getStudent());

        verify(studentDAO).findById(instance.getStudentId().getId());
    }

    @Test
    void addStudentToCourse_shouldReturnTrue_whenStudentIsAddedAtCourse() {
        Student studentWithCourse = instance.getStudent();

        when(courseDAO.findById(anyLong())).thenReturn(Optional.of(course));
        when(studentDAO.save(any(Student.class))).thenReturn(studentWithCourse);

        assertTrue(studentService.addStudentToCourse(instance.getStudent(), instance.getCourseId()));

        verify(courseDAO).findById(instance.getCourseId().getId());
        verify(studentDAO).save(studentWithCourse);
    }

    @Test
    void addStudentToCourse_shouldThrowInvalidIdException_whenCourseIdIsNotExist() {
        when(courseDAO.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> studentService.addStudentToCourse(instance.getStudent(), instance.getCourseId()));

        verify(courseDAO).findById(instance.getCourseId().getId());
    }

    @Test
    void addStudentToCourse_shouldReturnFalse_whenStudentAlreadyEnrollmentToGivenCourse(){
        Student studentWithCourse = instance.getStudent();
        studentWithCourse.setCourses(instance.getCourseSet());

        when(courseDAO.findById(anyLong())).thenReturn(Optional.of(course));

        assertFalse(studentService.addStudentToCourse(studentWithCourse, instance.getCourseId()));

        verify(courseDAO).findById(instance.getCourseId().getId());
    }

    @Test
    void removeStudentFromCourse_shouldReturnTrue_whenIsRemoved() {
        Student student1 = instance.getStudent();
        student1.setCourses(instance.getCourseSet());
        course.getStudents().add(student1);

        when(courseDAO.findById(anyLong())).thenReturn(Optional.of(course));
        when(studentDAO.save(any(Student.class))).thenReturn(student1);

        studentService.removeStudentFromCourse(instance.getStudent(), instance.getCourseId());

        verify(courseDAO).findById(instance.getCourseId().getId());
        verify(studentDAO).save(student1);
    }

    @Test
    void removeStudentFromCourse_shouldThrowInvalidIdException_whenCourseIdNotExist() {
        when(courseDAO.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> studentService.removeStudentFromCourse(instance.getStudent(), instance.getCourseId()));

        verify(courseDAO).findById(instance.getCourseId().getId());
    }

    @Test
    void removeStudentFromCourse_shouldReturnFalse_whenCourseNotRelateToStudent(){
        Student student1 = instance.getStudent();
        student1.addCourse(instance.getCoursesList().get(1));
        student1.addCourse(instance.getCoursesList().get(2));

        when(courseDAO.findById(anyLong())).thenReturn(Optional.of(instance.getCourse()));

        assertFalse(studentService.removeStudentFromCourse(student1, instance.getCourseId()));

        verify(courseDAO).findById(instance.getCourseId().getId());
    }

    @Test
    void getStudent_shouldReturnStudent_whenIdExist(){
        when(studentDAO.findById(anyLong())).thenReturn(Optional.of(instance.getStudent()));

        assertThat(studentService.getStudent(instance.getStudentId())).isSameAs(instance.getStudent());

        verify(studentDAO).findById(instance.getStudentId().getId());
    }

    @Test
    void getStudent_shouldThrowInvalidIdException_whenIdExist(){
        when(studentDAO.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> studentService.getStudent(instance.getStudentId()));
        verify(studentDAO).findById(instance.getStudentId().getId());
    }


}
