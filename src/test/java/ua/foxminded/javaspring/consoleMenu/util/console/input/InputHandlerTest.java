package ua.foxminded.javaspring.consoleMenu.util.console.input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.service.StudentService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;
import ua.foxminded.javaspring.consoleMenu.util.MyScanner;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

import java.util.InputMismatchException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class InputHandlerTest {

    @Mock
    private MyScanner scanner;
    @Mock
    private ConsolePrinter consolePrinter;
    @Mock
    private StudentService studentService;
    @Mock
    private ApplicationMessages messages;

    @InjectMocks
    private InputHandler inputHandler;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @ValueSource(strings = {"s", "1", "x"})
    void inputOptionMenu_shouldReturnEnteredCharacter_whenIsCalled(String s) {
        when(scanner.getLine()).thenReturn(s);

        assertThat(inputHandler.inputOptionMenu()).isEqualTo(s);
    }

    @Test
    void getDataOfNewStudent_shouldReturnStudentObject_whenEnteredValidData() {
        Student student = new Student("firstName", "lastName");
        student.setGroup(new Group(1L));


        when(scanner.getLine()).thenReturn("firstName").thenReturn("lastName");
        when(scanner.getLong()).thenReturn(1L);

        assertThat(inputHandler.getDataOfNewStudent()).usingRecursiveComparison().isEqualTo(student);

        verify(scanner, times(2)).getLine();
        verify(scanner).getLong();
        verify(consolePrinter).printAllGroups();
        verify(consolePrinter, times(4)).print(any());
    }

    @Test
    void getDataOfNewStudent_shouldThrowsInputMismatchException_whenEnteredNotValidData() {
        when(scanner.getLine()).thenReturn("firstName").thenReturn("last547Name");

        assertThrows(InputMismatchException.class, () -> inputHandler.getDataOfNewStudent());

        verify(scanner, times(2)).getLine();
        verify(consolePrinter, times(3)).print(any());
    }

    @Test
    void verifyValidStudent() {
        ReflectionTestUtils.setField(messages, "confirmStudentDetails", "Student: %s %s!");
        Student student = new Student("firstName", "lastName");

        when(studentService.getStudent(any(Student.class))).thenReturn(student);
        when(scanner.getLine()).thenReturn("yes");

        assertThat(inputHandler.verifyValidStudent(student)).isTrue();

        verify(studentService).getStudent(student);
        verify(consolePrinter).print(anyString());
        verify(scanner).getLine();
    }

    @Test
    void getCourse_shouldReturnCourseObject_whenEnteredValidData() {
        when(scanner.getLong()).thenReturn(1L);

        assertThat(inputHandler.getCourse()).usingRecursiveComparison().isEqualTo(new Course(1L));

        verify(scanner).getLong();
    }

    @Test
    void getGroup_shouldReturnGroupObject_whenEnteredValidData() {
        when(scanner.getLong()).thenReturn(1L);

        assertThat(inputHandler.getGroup()).usingRecursiveComparison().isEqualTo(new Group(1L));

        verify(scanner).getLong();
    }

    @Test
    void getEnrollment_shouldReturnStudentObject_whenEnteredValidData() {
        when(scanner.getLong()).thenReturn(1L);

        assertThat(inputHandler.getStudent()).usingRecursiveComparison().isEqualTo(new Student(1L));

        verify(scanner).getLong();
    }

    @Test
    void getStudent_shouldReturnStudentAtCourseObject_whenEnteredValidData() {
        when(scanner.getLong()).thenReturn(1L);

        assertThat(inputHandler.getEnrollment()).usingRecursiveComparison().isEqualTo(new StudentAtCourse(1L));

        verify(scanner).getLong();
    }

    @Test
    void getRequiredAmountOfStudents() {
        int expect = 29;
        when(scanner.getInt()).thenReturn(29);

        assertThat(inputHandler.getRequiredAmountOfStudents()).isEqualTo(expect);

        verify(scanner).getInt();
    }
}