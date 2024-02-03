package ua.foxminded.javaspring.consoleMenu;

import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;

public class TestData {

    public final String courseName = "Introduction to Psychology";
    public final String courseDescription = "Learn the basics of psychology";

    public final Course course = new Course(4L, courseName, courseDescription);

    public final String groupName = "YS-28";
    public final Group group = new Group(4L, groupName);

    public final String studentFirstName = "Ava";
    public final String studentLastName = "Rodriguez";
    public final Student student = new Student(6L, studentFirstName, studentLastName, new ItemInstance().getGroup());

    public Course getCourse() {
        return course;
    }

    public Group getGroup() {
        return group;
    }

    public Student getStudent() {
        return student;
    }
}

