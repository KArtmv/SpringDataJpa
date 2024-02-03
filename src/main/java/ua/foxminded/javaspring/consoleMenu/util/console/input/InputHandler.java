package ua.foxminded.javaspring.consoleMenu.util.console.input;

import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.service.StudentService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;
import ua.foxminded.javaspring.consoleMenu.util.MyScanner;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

import java.util.InputMismatchException;

public class InputHandler {
    private static final String ALPHABETIC_PATTERN = "\\p{Alpha}+";
    private MyScanner scanner;
    private ConsolePrinter consolePrinter;
    private StudentService studentService;
    private ApplicationMessages messages;

    @Autowired
    public InputHandler(MyScanner scanner, ConsolePrinter consolePrinter, StudentService studentService, ApplicationMessages messages) {
        this.scanner = scanner;
        this.consolePrinter = consolePrinter;
        this.studentService = studentService;
        this.messages = messages;
    }

    public String inputOptionMenu() {
        String s = scanner.getLine();
        if (s.equals("x")) {
            scanner.close();
        }
        return s;
    }

    public Student getDataOfNewStudent() {
        Student student = new Student();
        consolePrinter.print(messages.inputNewStudentData);
        consolePrinter.print(messages.inputStudentFirstName);
        student.setFirstName(getString());
        consolePrinter.print(messages.inputStudentLastName);
        student.setLastName(getString());
        consolePrinter.print(messages.addStudentToGroup);
        consolePrinter.printAllGroups();
        student.setGroup(getGroup());

        return student;
    }

    private String getString() {
        String s = scanner.getLine();
        if (s.matches(ALPHABETIC_PATTERN)) {
            return s;
        } else {
            throw new InputMismatchException(String.format("Input: '%s', does not match the alphabetic pattern.", s));
        }
    }

    public boolean verifyValidStudent(Student student) {
        Student selectedStudent = studentService.getStudent(student);
        consolePrinter.print(String.format(messages.confirmStudentDetails,
                selectedStudent.getId(), selectedStudent.getFirstName(), selectedStudent.getLastName()));

        return scanner.getLine().equalsIgnoreCase("yes");
    }

    public Course getCourse() {
        Course course = new Course();
        course.setId(scanner.getLong());
        return course;
    }

    public Group getGroup() {
        Group group = new Group();
        group.setId(scanner.getLong());
        return group;
    }

    public Student getStudent() {
        Student student = new Student();
        student.setId(scanner.getLong());
        return student;
    }

    public StudentAtCourse getEnrollment() {
        StudentAtCourse studentAtCourse = new StudentAtCourse();
        studentAtCourse.setId(scanner.getLong());
        return studentAtCourse;
    }

    public Integer getRequiredAmountOfStudents() {
        return scanner.getInt();
    }
}
