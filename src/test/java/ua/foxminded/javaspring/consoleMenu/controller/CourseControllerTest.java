package ua.foxminded.javaspring.consoleMenu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.javaspring.consoleMenu.exception.InvalidIdException;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.service.CourseService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;
import ua.foxminded.javaspring.consoleMenu.util.console.input.InputHandler;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseControllerTest {

    private final Course course = new Course(1L);
    @Mock
    private CourseService courseService;
    @Mock
    private ConsolePrinter consolePrinter;
    @Mock
    private ApplicationMessages messages;
    @Mock
    private InputHandler inputHandler;

    @InjectMocks
    CourseController courseController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void allStudentsFromCourse_shouldPrintAllStudentsAtReceivedCourse_whenStudentsAreAtCourse() {
        List<StudentAtCourse> studentsFromCourse = new ArrayList<>();
        studentsFromCourse.add(new StudentAtCourse(new Student("firstName1", "lastName1"), course));
        studentsFromCourse.add(new StudentAtCourse(new Student("firstName2", "lastName2"), course));
        studentsFromCourse.add(new StudentAtCourse(new Student("firstName3", "lastName3"), course));

        when(inputHandler.getCourse()).thenReturn(course);
        when(courseService.allStudentsFromCourse(course)).thenReturn(studentsFromCourse);

        courseController.allStudentsFromCourse();

        verify(consolePrinter).printAllCourses();
        verify(consolePrinter).print(messages.inputCourseIdToViewEnrolledStudents);
        verify(inputHandler).getCourse();
        verify(courseService).allStudentsFromCourse(course);
        verify(consolePrinter).viewAllStudentsFromCourse(studentsFromCourse);
    }

    @Test
    void allStudentsFromCourse_shouldDoNothing_whenStudentsAreNotAtCourse() {
        List<StudentAtCourse> studentsFromCourse = new ArrayList<>();

        when(inputHandler.getCourse()).thenReturn(course);
        when(courseService.allStudentsFromCourse(course)).thenReturn(studentsFromCourse);

        courseController.allStudentsFromCourse();

        verify(consolePrinter).printAllCourses();
        verify(inputHandler).getCourse();
        verify(courseService).allStudentsFromCourse(course);
    }

    @Test
    void allStudentsFromCourse_shouldThrowsInvalidIdException_whenCourseIsNotExist() {
        when(inputHandler.getCourse()).thenReturn(course);
        when(courseService.allStudentsFromCourse(course)).thenThrow(InvalidIdException.class);

        courseController.allStudentsFromCourse();

        verify(consolePrinter).printAllCourses();
        verify(consolePrinter).print(messages.inputCourseIdToViewEnrolledStudents);
        verify(inputHandler).getCourse();
        verify(courseService).allStudentsFromCourse(course);
    }

    @Test
    void allStudentsFromCourse_shouldThrowsInputMismatchException_whenInputNotNumeric() {
        when(inputHandler.getCourse()).thenThrow(InputMismatchException.class);

        courseController.allStudentsFromCourse();

        verify(consolePrinter).printAllCourses();
        verify(consolePrinter).print(messages.inputCourseIdToViewEnrolledStudents);
        verify(inputHandler).getCourse();
    }
}