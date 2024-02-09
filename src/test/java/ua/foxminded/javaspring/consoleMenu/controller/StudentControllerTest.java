package ua.foxminded.javaspring.consoleMenu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.javaspring.consoleMenu.ItemInstance;
import ua.foxminded.javaspring.consoleMenu.exception.InvalidIdException;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.service.StudentService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;
import ua.foxminded.javaspring.consoleMenu.util.console.input.InputHandler;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

import java.util.InputMismatchException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    private final ItemInstance instance = new ItemInstance();

    private Course course;
    private Student student;
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
        course = instance.getCourse();
        student = instance.getStudent();
    }

    @Test
    void addNewStudent_shouldReturnTrue_whenStudentIsAdded() {
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
        when(inputHandler.getStudent()).thenReturn(instance.getStudentId());
        when(studentService.getStudent(any(Student.class))).thenReturn(student);
        when(inputHandler.verifyValidStudent(any(Student.class))).thenReturn(true);

        studentController.deleteStudent();

        verify(inputHandler).getStudent();
        verify(studentService).getStudent(instance.getStudentId());
        verify(inputHandler).verifyValidStudent(student);
        verify(studentService).deleteStudent(student);
    }

    @Test
    void deleteStudent_shouldThrowsInvalidIdException_whenReceivedStudentNotExist() {
        when(inputHandler.getStudent()).thenReturn(instance.getStudentId());
        when(studentService.getStudent(any(Student.class))).thenThrow(InvalidIdException.class);

        studentController.deleteStudent();

        verify(inputHandler).getStudent();
        verify(studentService).getStudent(instance.getStudentId());
    }

    @Test
    void deleteStudent_shouldThrowsInputMismatchException_whenInputMismatch() {
        when(inputHandler.getStudent()).thenThrow(InputMismatchException.class);

        studentController.deleteStudent();

        verify(inputHandler).getStudent();
    }

    @Test
    void addStudentToCourse_shouldDoNothing_whenSuccessfullyAdded() {
        when(inputHandler.getStudent()).thenReturn(instance.getStudentId());
        when(studentService.getStudent(any(Student.class))).thenReturn(student);
        when(inputHandler.verifyValidStudent(any(Student.class))).thenReturn(true);
        when(inputHandler.getCourse()).thenReturn(course);
        when(studentService.addStudentToCourse(any(Student.class), any(Course.class))).thenReturn(true);

        studentController.addStudentToCourse();

        verify(inputHandler).getStudent();
        verify(studentService).getStudent(instance.getStudentId());
        verify(inputHandler).verifyValidStudent(student);
        verify(consolePrinter).printAllCourses();
        verify(inputHandler).getCourse();
        verify(studentService).addStudentToCourse(student, course);
        verify(consolePrinter, times(3)).print(any());
    }

    @Test
    void addStudentToCourse_shouldThrowsInvalidIdException_whenReceivedStudentNotExist() {
        when(inputHandler.getStudent()).thenReturn(instance.getStudentId());
        when(studentService.getStudent(any(Student.class))).thenThrow(InvalidIdException.class);

        studentController.addStudentToCourse();

        verify(consolePrinter).print(any());
        verify(inputHandler).getStudent();
        verify(studentService).getStudent(instance.getStudentId());
    }

    @Test
    void addStudentToCourse_shouldThrowsInputMismatchException_whenInputMismatch() {
        when(inputHandler.getStudent()).thenThrow(InputMismatchException.class);

        studentController.addStudentToCourse();

        verify(inputHandler).getStudent();
    }

    @Test
    void removeStudentFromCourse_doNothing_whenSuccessfullyRemoved() {
        student.setCourses(instance.getCourseSet());

        when(inputHandler.getStudent()).thenReturn(instance.getStudentId());
        when(studentService.getStudent(any(Student.class))).thenReturn(student);
        when(inputHandler.verifyValidStudent(any(Student.class))).thenReturn(true);
        when(inputHandler.getCourse()).thenReturn(instance.getCourseId());
        when(studentService.removeStudentFromCourse(any(Student.class), any(Course.class))).thenReturn(true);

        studentController.removeStudentFromCourse();

        verify(inputHandler).getStudent();
        verify(studentService).getStudent(instance.getStudentId());
        verify(inputHandler).verifyValidStudent(student);
        verify(consolePrinter).viewAllCoursesOfStudent(student);
        verify(inputHandler).getCourse();
        verify(consolePrinter, times(3)).print(any());
    }

    @Test
    void removeStudentFromCourse_shouldThrowsInvalidIdException_whenReceivedStudentNotExist() {
        when(inputHandler.getStudent()).thenReturn(instance.getStudentId());
        when(studentService.getStudent(any(Student.class))).thenThrow(InvalidIdException.class);

        studentController.removeStudentFromCourse();

        verify(inputHandler).getStudent();
        verify(studentService).getStudent(instance.getStudentId());
        verify(consolePrinter).print(any());
    }

    @Test
    void removeStudentFromCourse_shouldThrowsInputMismatchException_whenReceivedStudentDataNotMatchAlphabeticCharacter() {
        when(inputHandler.getStudent()).thenThrow(InputMismatchException.class);

        studentController.addStudentToCourse();

        verify(consolePrinter).print(any());
        verify(inputHandler).getStudent();
    }

    @Test
    void removeStudentFromCourse_doNothing_whenStudentHasNotAnyCourse() {
        when(inputHandler.getStudent()).thenReturn(instance.getStudentId());
        when(studentService.getStudent(any(Student.class))).thenReturn(student);

        studentController.removeStudentFromCourse();

        verify(inputHandler).getStudent();
        verify(studentService).getStudent(any(Student.class));
        verify(consolePrinter, times(2)).print(any());
    }
}