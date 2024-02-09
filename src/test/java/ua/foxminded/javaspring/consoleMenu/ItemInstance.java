package ua.foxminded.javaspring.consoleMenu;

import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemInstance {

    private final Student studentId = new Student(5L);
    private final Course courseId = new Course(1L);
    private final Group groupId = new Group(1L);

    private final Group group = new Group(1L, "YS-27");
    private final Student student = new Student(5L, "Amelia", "Martinez", group);
    private final Course course = new Course(1L, "Principles of Economics", "Learn about the fundamentals of economics");

    private final Student expectedStudent = new Student(6L, "Ava", "Rodriguez", group);

    private final List<Course> coursesList = Arrays.asList(
            course,
            new Course(2L, "World History: Ancient Civilizations", "Explore the history of ancient civilizations"),
            new Course(3L, "Creative Writing Workshop", "Develop your writing skills in a creative environment")
    );
    private final List<Group> groupList = Arrays.asList(
            new Group(1L, "YS-27"),
            new Group(2L, "HV-14"),
            new Group(3L, "QM-09"));
    private final List<CounterStudentsAtGroup> studentsAtGroups = Arrays.asList(
            new CounterStudentsAtGroup("group1", 10L),
            new CounterStudentsAtGroup("group2", 1L),
            new CounterStudentsAtGroup("group3", 5L));

    private final Set<Course> courseSet = new HashSet<>(coursesList);
    private final Set<Student> studentsSet = new HashSet<>(Arrays.asList(
            new Student(1L, "Liam", "Jones"),
            new Student(2L, "Harper", "Robinson"),
            new Student(3L, "Lucas", "Thompson"),
            new Student(4L, "Carter", "Clark"),
            student));

    public List<Group> getGroupList() {
        return groupList;
    }

    public List<Course> getCoursesList() {
        return coursesList;
    }

    public Student getExpectedStudent() {
        return expectedStudent;
    }

    public Group getGroupId() {
        return groupId;
    }

    public Course getCourseId() {
        return courseId;
    }

    public Set<Course> getCourseSet() {
        return courseSet;
    }

    public Student getStudentId() {
        return studentId;
    }

    public List<CounterStudentsAtGroup> getStudentsAtGroups() {
        return studentsAtGroups;
    }

    public Set<Student> getStudentsSet() {
        return studentsSet;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Group getGroup() {
        return group;
    }
}
