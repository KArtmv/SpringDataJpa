package ua.foxminded.javaspring.consoleMenu.util.console.output;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import ua.foxminded.javaspring.consoleMenu.ItemInstance;
import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.menu.Menu;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
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

    private final ItemInstance instance = new ItemInstance();
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
    void viewAllCoursesOfStudent_shouldPrintStudentAndCourseInformation_WhenStudentHasCourses() {
        ReflectionTestUtils.setField(messages, "printStudentEnrolledInCourses", "Student: %s %s.");
        ReflectionTestUtils.setField(messages, "printCourseDetails", "Course: ID %s, %s. Description: %s.");
        System.setOut(new PrintStream(outputStream));

        Student student = instance.getStudent();
        student.getCourses().add(instance.getCourse());

        consolePrinter.viewAllCoursesOfStudent(student);

        String expect = "Student: Amelia Martinez.\n" +
                "Course: ID 1, Principles of Economics. Description: Learn about the fundamentals of economics.";

        assertThat(outputStream.toString().trim()).isEqualTo(expect);
    }

    @Test
    void viewAllStudentsFromCourse_shouldPrintCourseAndStudentInformation_WhenStudentsExistInCourse() {
        ReflectionTestUtils.setField(messages, "printStudentsEnrolledInCourse", "Course: %s.");
        System.setOut(new PrintStream(outputStream));

        Course course = instance.getCourse();
        course.getStudents().add(instance.getStudent());

        consolePrinter.viewAllStudentsFromCourse(course);

        String expect = "Course: Principles of Economics.\n" +
                "Amelia Martinez";

        assertThat(outputStream.toString().trim()).isEqualTo(expect);
    }

    @Test
    void viewAmountStudentAtGroup_shouldPrintCountOfStudentsAndGroupName_WhenCounterStudentsExist() {
        ReflectionTestUtils.setField(messages, "printGroupByCountEnrollmentStudents", "Count of student: %s, group: %s.");

        List<CounterStudentsAtGroup> counterStudents = Collections.singletonList(new CounterStudentsAtGroup("someGroup", 1L));

        System.setOut(new PrintStream(outputStream));

        consolePrinter.viewAmountStudentAtGroup(counterStudents);

        String expect = "Count of student: 1, group: someGroup.";

        assertThat(outputStream.toString().trim()).isEqualTo(expect);
    }

    @Test
    void printAllCourses_shouldPrintCourseInformation_WhenCoursesExist() {
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
    void printAllGroups_shouldPrintGroupInformation_WhenGroupsExist() {
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
    void printMenu_shouldPrintMenuOptions_WhenMenuHasOptions() {
        System.setOut(new PrintStream(outputStream));

        String expect = "some options";

        when(menu.getOptions()).thenReturn("some options");

        consolePrinter.printMenu();

        assertThat(outputStream.toString().trim()).isEqualTo(expect);
        verify(menu).getOptions();
    }

    @Test
    void print_shouldPrintText_WhenGivenTextToPrint() {
        System.setOut(new PrintStream(outputStream));

        String expect = "some text";

        consolePrinter.print("some text");

        assertThat(outputStream.toString().trim()).isEqualTo(expect);
    }
}