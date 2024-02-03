package ua.foxminded.javaspring.consoleMenu;

import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

public class ItemInstance {

    private final Group group = new Group(1L, "YS-27");
    private final Student student = new Student(5L, "Amelia", "Martinez", group);
    private final Course course = new Course(1L, "Principles of Economics", "Learn about the fundamentals of economics");
    private final StudentAtCourse studentAtCourse = new StudentAtCourse(11L, student, course);


    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Group getGroup() {
        return group;
    }

    public StudentAtCourse getStudentAtCourse() {
        return studentAtCourse;
    }
}
