package ua.foxminded.javaspring.consoleMenu.util.console.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.menu.Menu;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.service.CourseService;
import ua.foxminded.javaspring.consoleMenu.service.GroupService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;

import java.util.Comparator;
import java.util.List;

public class ConsolePrinter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsolePrinter.class);
    private GroupService groupService;
    private CourseService courseService;
    private Menu menu;
    private ApplicationMessages messages;

    @Autowired
    public ConsolePrinter(GroupService groupService, CourseService courseService, Menu menu, ApplicationMessages messages) {
        this.groupService = groupService;
        this.courseService = courseService;
        this.menu = menu;
        this.messages = messages;
    }

    public void viewAllCoursesOfStudent(Student student) {
        print(String.format(messages.printStudentEnrolledInCourses,
                student.getFirstName(), student.getLastName()));

        student.getCourses().stream().sorted(Comparator.comparingLong(Course::getId))
                .forEach(course -> print(String.format(
                    messages.printCourseDetails,
                    course.getId(),
                    course.getCourseName(),
                    course.getCourseDescription())));
    }

    public void viewAllStudentsFromCourse(Course course) {
        print(String.format(messages.printStudentsEnrolledInCourse, course.getCourseName()));
        course.getStudents().stream().sorted(Comparator.comparing(Student::getFirstName)).forEach(student ->
                print(String.format("%s %s", student.getFirstName(), student.getLastName())));
    }

    public void viewAmountStudentAtGroup(List<CounterStudentsAtGroup> studentsAtGroups) {
        studentsAtGroups.forEach(studentsAtGroup ->
                print(String.format(messages.printGroupByCountEnrollmentStudents,
                        studentsAtGroup.getStudentsCount(), studentsAtGroup.getGroupName())));
    }

    public void printAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (!CollectionUtils.isEmpty(courses)) {
            courses.forEach(course -> print(String.format(messages.printCourseDetails,
                    course.getId(), course.getCourseName(), course.getCourseDescription())));
        } else {
            LOGGER.info("Failed to get list of courses.");
        }
    }

    public void printAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        if (!CollectionUtils.isEmpty(groups)) {
            groups.forEach(group -> print(String.format(messages.printAllGroups, group.getId(), group.getGroupName())));
        } else {
            LOGGER.info("Failed to get list of courses.");
        }
    }

    public void printMenu() {
        print(menu.getOptions());
    }

    public void print(String string) {
        System.out.print(string + "\n");
    }
}
