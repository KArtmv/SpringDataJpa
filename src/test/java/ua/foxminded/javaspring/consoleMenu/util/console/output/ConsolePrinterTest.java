package ua.foxminded.javaspring.consoleMenu.util.console.output;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.menu.Menu;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.service.CourseService;
import ua.foxminded.javaspring.consoleMenu.service.GroupService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConsolePrinterTest {

    private final Student student = new Student("firstName", "firstName");
    private final Course course = new Course("courseName", "courseDescription");
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Mock
    private GroupService groupService;
    @Mock
    private CourseService courseService;
    @Mock
    private Menu menu;
    @Mock
    private ApplicationMessages messages;

    @InjectMocks
    private ConsolePrinter consolePrinter;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void viewAllCoursesOfStudent_ShouldPrintStudentAndCourseInformation_WhenStudentHasCourses() {
        ReflectionTestUtils.setField(messages, "printStudentEnrolledInCourses", "Student: %s %s.");
        ReflectionTestUtils.setField(messages, "printCourseDetails", "Course: ID %s, %s. Description: %s.");

        List<StudentAtCourse> studentCourses = Collections.singletonList(new StudentAtCourse(10L, student, course));

        System.setOut(new PrintStream(outputStream));

        consolePrinter.viewAllCoursesOfStudent(studentCourses);

        String expect = "Student: firstName firstName.\n" + "Course: ID 10, courseName. Description: courseDescription.";

        assertThat(outputStream.toString().trim()).isEqualTo(expect);
    }

    @Test
    void viewAllStudentsFromCourse_ShouldPrintCourseAndStudentInformation_WhenStudentsExistInCourse() {
        ReflectionTestUtils.setField(messages, "printStudentsEnrolledInCourse", "Course: %s.");

        List<StudentAtCourse> studentsAtCourse = Collections.singletonList(new StudentAtCourse(student, course));

        System.setOut(new PrintStream(outputStream));

        consolePrinter.viewAllStudentsFromCourse(studentsAtCourse);

        String expect = "Course: courseName.\n" + "firstName firstName";

        assertThat(outputStream.toString().trim()).isEqualTo(expect);
    }

    @Test
    void viewAmountStudentAtGroup_ShouldPrintCountOfStudentsAndGroupName_WhenCounterStudentsExist() {
        ReflectionTestUtils.setField(messages, "printGroupByCountEnrollmentStudents", "Count of student: %s, group: %s.");

        List<CounterStudentsAtGroup> counterStudents = Collections.singletonList(new CounterStudentsAtGroup("someGroup", 1L));

        System.setOut(new PrintStream(outputStream));

        consolePrinter.viewAmountStudentAtGroup(counterStudents);

        String expect = "Count of student: 1, group: someGroup.";

        assertThat(outputStream.toString().trim()).isEqualTo(expect);
    }

    @Test
    void printAllCourses_ShouldPrintCourseInformation_WhenCoursesExist() {
        ReflectionTestUtils.setField(messages, "printCourseDetails", "ID: %s, Course: %s, description: %s.");
        System.setOut(new PrintStream(outputStream));

        List<Course> courses = Collections.singletonList(new Course(1L, "courseName", "courseDescription"));

        when(courseService.getAllCourses()).thenReturn(courses);

        consolePrinter.printAllCourses();

        String expect = "ID: 1, Course: courseName, description: courseDescription.";

        assertThat(outputStream.toString().trim()).isEqualTo(expect);
        verify(courseService).getAllCourses();
    }

    @Test
    void printAllGroups_ShouldPrintGroupInformation_WhenGroupsExist() {
        ReflectionTestUtils.setField(messages, "printAllGroups", "ID: %s, Group: %s.");
        System.setOut(new PrintStream(outputStream));

        List<Group> groups = Collections.singletonList(new Group(1L, "groupName"));
        String expect = "ID: 1, Group: groupName.";

        when(groupService.getAllGroups()).thenReturn(groups);

        consolePrinter.printAllGroups();

        assertThat(outputStream.toString().trim()).isEqualTo(expect);
        verify(groupService).getAllGroups();
    }

    @Test
    void printMenu_ShouldPrintMenuOptions_WhenMenuHasOptions() {
        System.setOut(new PrintStream(outputStream));

        String expect = "some options";

        when(menu.getOptions()).thenReturn("some options");

        consolePrinter.printMenu();

        assertThat(outputStream.toString().trim()).isEqualTo(expect);
        verify(menu).getOptions();
    }

    @Test
    void print_ShouldPrintText_WhenGivenTextToPrint() {
        System.setOut(new PrintStream(outputStream));

        String expect = "some text";

        consolePrinter.print("some text");

        assertThat(outputStream.toString().trim()).isEqualTo(expect);
    }
}