package ua.foxminded.javaspring.consoleMenu;

import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;

import java.util.Arrays;
import java.util.List;

public class TestData {

//    public final String courseName = "Introduction to Psychology";
//    public final String courseDescription = "Learn the basics of psychology";

//    private final Course course = new Course(4L, courseName, courseDescription);

//    public final String groupName = "YS-28";
//    private final Group group = new Group(4L, groupName);

    public final String studentFirstName = "Ava";
    public final String studentLastName = "Rodriguez";
//    private final Student student = new Student(6L, studentFirstName, studentLastName, new ItemInstance().getGroup());

    private List<CounterStudentsAtGroup> studentsAtGroupList = Arrays.asList(
            new CounterStudentsAtGroup("ZF-83", 3L),
            new CounterStudentsAtGroup("WU-48", 1L),
            new CounterStudentsAtGroup("PL-41", 2L),
            new CounterStudentsAtGroup("HV-14", 5L),
            new CounterStudentsAtGroup("Students without group", 1L),
            new CounterStudentsAtGroup("TC-63", 3L),
            new CounterStudentsAtGroup("QM-09", 3L),
            new CounterStudentsAtGroup("RG-52", 5L),
            new CounterStudentsAtGroup("KX-76", 1L),
            new CounterStudentsAtGroup("YS-27", 1L),
            new CounterStudentsAtGroup("ND-98", 2L));

    public List<CounterStudentsAtGroup> getStudentsAtGroupList() {
        return studentsAtGroupList;
    }

//    public Course getCourse() {
//        return course;
//    }
//
//    public Group getGroup() {
//        return group;
//    }
//
//    public Student getStudent() {
//        return student;
//    }
}

