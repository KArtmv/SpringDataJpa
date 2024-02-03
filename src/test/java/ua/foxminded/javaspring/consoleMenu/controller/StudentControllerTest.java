package ua.foxminded.javaspring.consoleMenu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;
import ua.foxminded.javaspring.consoleMenu.exception.InvalidIdException;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.service.StudentService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;
import ua.foxminded.javaspring.consoleMenu.util.console.input.InputHandler;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    private final Course course = new Course(1L);
    private final Student student = new Student(1L, "firstName", "lastName", new Group(1L));
    @Mock
    private StudentService studentService;
    @Mock
    private ConsolePrinter consolePrinter;
    @Mock
    private InputHandler inputHandler;
    @Mock
    private ApplicationMessages messages;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNewStudent_should() {
        when(inputHandler.getDataOfNewStudent()).thenReturn(student);
        when(studentService.addNewStudent(any(Student.class))).thenReturn(true);

        studentController.addNewStudent();

        verify(inputHandler).getDataOfNewStudent();
        verify(studentService).addNewStudent(student);
        verify(consolePrinter).print(messages.printStudentAddedSuccess);
    }

    @Test
    void addNewStudent_shouldThrowsInputMismatchException_whenInputMismatch() {
        when(inputHandler.getDataOfNewStudent()).thenThrow(InputMismatchException.class);

        studentController.addNewStudent();

        verify(inputHandler).getDataOfNewStudent();
    }

    @Test
    void addNewStudent_shouldThrowsInvalidIdException_whenGroupIdIsNotFounded() {
        when(inputHandler.getDataOfNewStudent()).thenReturn(student);
        when(studentService.addNewStudent(any(Student.class))).thenThrow(InvalidIdException.class);

        studentController.addNewStudent();

        verify(inputHandler).getDataOfNewStudent();
    }

    @Test
    void deleteStudent_shouldRemovedSuccessfully_whenReceivedStudentExist() {
        when(inputHandler.getStudent()).thenReturn(student);
        when(inputHandler.verifyValidStudent(any(Student.class))).thenReturn(true);
        when(studentService.deleteStudent(any(Student.class))).thenReturn(true);

        studentController.deleteStudent();

        verify(inputHandler).getStudent();
        verify(inputHandler).verifyValidStudent(student);
        verify(studentService).deleteStudent(student);
    }

    @Test
    void deleteStudent_shouldThrowsInvalidIdException_whenReceivedStudentNotExist() {
        when(inputHandler.getStudent()).thenReturn(student);
        when(inputHandler.verifyValidStudent(any(Student.class))).thenThrow(InvalidIdException.class);

        studentController.deleteStudent();

        verify(inputHandler).getStudent();
        verify(inputHandler).verifyValidStudent(student);
    }

    @Test
    void deleteStudent_shouldThrowsInputMismatchException_whenInputMismatch() {
        when(inputHandler.getStudent()).thenThrow(InputMismatchException.class);

        studentController.deleteStudent();

        verify(inputHandler).getStudent();
    }

    @Test
    void addStudentToCourse_shouldDoNothing_whenSuccessfullyAdded() {
        when(inputHandler.getStudent()).thenReturn(student);
        when(inputHandler.verifyValidStudent(any(Student.class))).thenReturn(true);
        when(inputHandler.getCourse()).thenReturn(course);
        when(studentService.addStudentToCourse(any(StudentAtCourse.class))).thenReturn(true);

        studentController.addStudentToCourse();

        verify(inputHandler).getStudent();
        verify(inputHandler).verifyValidStudent(student);
        verify(consolePrinter).printAllCourses();
        verify(inputHandler).getCourse();
        verify(studentService).addStudentToCourse(any(StudentAtCourse.class));
        verify(consolePrinter, times(3)).print(any());
    }

    @Test
    void addStudentToCourse_shouldThrowsInvalidIdException_whenReceivedStudentNotExist() {
        when(inputHandler.getStudent()).thenReturn(student);
        when(inputHandler.verifyValidStudent(any(Student.class))).thenThrow(InvalidIdException.class);

        studentController.addStudentToCourse();

        verify(consolePrinter).print(any());
        verify(inputHandler).getStudent();
        verify(inputHandler).verifyValidStudent(student);
    }

    @Test
    void addStudentToCourse_shouldThrowsInputMismatchException_whenInputMismatch() {
        when(inputHandler.getStudent()).thenThrow(InputMismatchException.class);

        studentController.addStudentToCourse();

        verify(inputHandler).getStudent();
    }

    @Test
    void addStudentToCourse_shouldThrowsDuplicateKeyException_whenDuplicateAdding() {
        when(inputHandler.getStudent()).thenReturn(student);
        when(inputHandler.verifyValidStudent(any(Student.class))).thenReturn(true);
        when(inputHandler.getCourse()).thenReturn(course);
        when(studentService.addStudentToCourse(any(StudentAtCourse.class))).thenThrow(DuplicateKeyException.class);

        studentController.addStudentToCourse();

        verify(inputHandler).getStudent();
        verify(inputHandler).verifyValidStudent(student);
        verify(consolePrinter).printAllCourses();
        verify(inputHandler).getCourse();
        verify(studentService).addStudentToCourse(any(StudentAtCourse.class));
        verify(consolePrinter, times(2)).print(any());
    }

    @Test
    void removeStudentFromCourse_doNothing_whenSuccessfullyRemoved() {
        List<StudentAtCourse> studentAtCourses = new ArrayList<>();
        studentAtCourses.add(new StudentAtCourse(student, course));
        studentAtCourses.add(new StudentAtCourse(student, new Course(2L)));
        studentAtCourses.add(new StudentAtCourse(student, new Course(3L)));
        StudentAtCourse enrollmentId = new StudentAtCourse(1L);

        when(inputHandler.getStudent()).thenReturn(student);
        when(studentService.getStudent(any(Student.class))).thenReturn(student);
        when(studentService.getAllCoursesOfStudent(any(Student.class))).thenReturn(studentAtCourses);
        when(inputHandler.verifyValidStudent(any(Student.class))).thenReturn(true);
        when(inputHandler.getEnrollment()).thenReturn(enrollmentId);
        when(studentService.removeStudentFromCourse(enrollmentId)).thenReturn(true);

        studentController.removeStudentFromCourse();

        verify(inputHandler).getStudent();
        verify(studentService).getStudent(any(Student.class));
        verify(studentService).getAllCoursesOfStudent(student);
        verify(inputHandler).verifyValidStudent(student);
        verify(consolePrinter).viewAllCoursesOfStudent(studentAtCourses);
        verify(inputHandler).getEnrollment();
        verify(consolePrinter, times(3)).print(any());
    }

    @Test
    void removeStudentFromCourse_shouldThrowsInvalidIdException_whenReceivedStudentNotExist() {
        when(inputHandler.getStudent()).thenReturn(student);
        when(studentService.getStudent(any(Student.class))).thenThrow(InvalidIdException.class);

        studentController.removeStudentFromCourse();

        verify(inputHandler).getStudent();
        verify(studentService).getStudent(student);
        verify(consolePrinter).print(any());
    }

    @Test
    void removeStudentFromCourse_shouldThrowsInputMismatchException_whenInputMismatch() {
        when(inputHandler.getStudent()).thenThrow(InputMismatchException.class);

        studentController.addStudentToCourse();

        verify(consolePrinter).print(any());
        verify(inputHandler).getStudent();
    }

    @Test
    void removeStudentFromCourse_doNothing_whenStudentHasNotAnyCourse() {
        List<StudentAtCourse> studentAtCourses = new ArrayList<>();

        when(inputHandler.getStudent()).thenReturn(student);
        when(studentService.getStudent(any(Student.class))).thenReturn(student);
        when(studentService.getAllCoursesOfStudent(any(Student.class))).thenReturn(studentAtCourses);

        studentController.removeStudentFromCourse();

        verify(inputHandler).getStudent();
        verify(studentService).getStudent(any(Student.class));
        verify(studentService).getAllCoursesOfStudent(student);
        verify(consolePrinter, times(2)).print(any());
    }
}