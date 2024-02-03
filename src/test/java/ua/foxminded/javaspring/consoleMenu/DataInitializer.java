package ua.foxminded.javaspring.consoleMenu;

import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import java.util.ArrayList;
import java.util.List;

public class DataInitializer {

    public List<Student> studentsListInit() {
        List<Student> students = new ArrayList<>();
        for (int id = 1; id <= 3; id++) {
            students.add(new Student((long) id, "firstname" + id, "lastName" + id, new Group((long) id)));
        }
        return students;
    }

    public List<Course> coursesListInit() {
        List<Course> courses = new ArrayList<>();
        for (int id = 1; id <= 3; id++) {
            courses.add(new Course("courseName" + id, "courseDescription" + id));
        }
        return courses;
    }

    public List<Group> groupsListInit() {
        List<Group> groups = new ArrayList<>();
        for (int id = 1; id <= 3; id++) {
            groups.add(new Group("groupName" + id));
        }
        return groups;
    }

    public List<StudentAtCourse> studentAtCourseListInit() {
        List<StudentAtCourse> studentAtCourses = new ArrayList<>();
        for (int id = 1; id <= 3; id++) {
            studentAtCourses.add(new StudentAtCourse((long) id, new Student((long) id, "firstname" + id, "lastName" + id),
                    new Course((long) id, "courseName" + id, "courseDescription" + id)));
        }
        return studentAtCourses;
    }
}
