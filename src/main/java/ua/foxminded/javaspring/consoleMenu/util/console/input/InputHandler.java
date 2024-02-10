package ua.foxminded.javaspring.consoleMenu.util.console.input;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;
import ua.foxminded.javaspring.consoleMenu.util.MyScanner;
import ua.foxminded.javaspring.consoleMenu.util.MyScannerImpl;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

import java.util.InputMismatchException;

@Component
public class InputHandler {
    private static final String ALPHABETIC_PATTERN = "\\p{Alpha}+";
    private MyScanner scanner;
    private ConsolePrinter consolePrinter;
    private ApplicationMessages messages;

    public InputHandler(MyScanner scanner, ConsolePrinter consolePrinter, ApplicationMessages messages) {
        this.scanner = scanner;
        this.consolePrinter = consolePrinter;
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
        consolePrinter.print(String.format(messages.confirmStudentDetails,
                student.getId(), student.getFirstName(), student.getLastName()));

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

    public Integer getRequiredAmountOfStudents() {
        return scanner.getInt();
    }
}
