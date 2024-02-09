package ua.foxminded.javaspring.consoleMenu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.javaspring.consoleMenu.ItemInstance;
import ua.foxminded.javaspring.consoleMenu.exception.InvalidIdException;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.service.CourseService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;
import ua.foxminded.javaspring.consoleMenu.util.console.input.InputHandler;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

import java.util.InputMismatchException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseControllerTest {

    private final ItemInstance instance = new ItemInstance();
    private Course course;

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
        course = instance.getCourse();
    }

    @Test
    void allStudentsFromCourse_shouldPrintAllStudentsAtReceivedCourse_whenStudentsAreAtCourse() {
        course.setStudents(instance.getStudentsSet());

        when(inputHandler.getCourse()).thenReturn(instance.getCourseId());
        when(courseService.getCourse(any(Course.class))).thenReturn(instance.getCourse());

        courseController.allStudentsFromCourse();

        verify(consolePrinter).printAllCourses();
        verify(consolePrinter).print(messages.inputCourseIdToViewEnrolledStudents);
        verify(inputHandler).getCourse();
        verify(courseService).getCourse(instance.getCourseId());
        verify(consolePrinter).viewAllStudentsFromCourse(course);
    }

    @Test
    void allStudentsFromCourse_shouldDoNothing_whenStudentsAreNotAtCourse() {
        when(inputHandler.getCourse()).thenReturn(instance.getCourse());
        when(courseService.getCourse(any(Course.class))).thenReturn(course);

        courseController.allStudentsFromCourse();

        verify(consolePrinter).printAllCourses();
        verify(inputHandler).getCourse();
        verify(courseService).getCourse(course);
    }

    @Test
    void allStudentsFromCourse_shouldThrowsInvalidIdException_whenCourseIsNotExist() {
        when(inputHandler.getCourse()).thenReturn(instance.getCourseId());
        when(courseService.getCourse(any(Course.class))).thenThrow(InvalidIdException.class);

        courseController.allStudentsFromCourse();

        verify(consolePrinter).printAllCourses();
        verify(consolePrinter).print(messages.inputCourseIdToViewEnrolledStudents);
        verify(inputHandler).getCourse();
        verify(courseService).getCourse(instance.getCourseId());
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