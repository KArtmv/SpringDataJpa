package ua.foxminded.javaspring.consoleMenu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import ua.foxminded.javaspring.consoleMenu.exception.InvalidIdException;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.service.StudentService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;
import ua.foxminded.javaspring.consoleMenu.util.console.input.InputHandler;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

import java.util.InputMismatchException;
import java.util.List;

@Controller
public class StudentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    private StudentService studentService;
    private ConsolePrinter consolePrinter;
    private InputHandler inputHandler;
    private ApplicationMessages messages;

    @Autowired
    public StudentController(StudentService studentService, ConsolePrinter consolePrinter, InputHandler inputHandler, ApplicationMessages messages) {
        this.studentService = studentService;
        this.consolePrinter = consolePrinter;
        this.inputHandler = inputHandler;
        this.messages = messages;
    }

    public void addNewStudent() {
        LOGGER.info("Run method: addNewStudent");
        try {
            if (studentService.addNewStudent(inputHandler.getDataOfNewStudent())) {
                consolePrinter.print(messages.printStudentAddedSuccess);
                LOGGER.debug("Student is added.");
            }
        } catch (InputMismatchException | InvalidIdException e) {
            LOGGER.error("Failed add student: {}.", e.getMessage());
        }
    }

    public void deleteStudent() {
        LOGGER.info("Run method: deleteStudent");
        try {
            consolePrinter.print(messages.inputStudentIdToRemove);
            Student student = inputHandler.getStudent();
            LOGGER.debug("Received student ID: {}", student.getId());

            if (inputHandler.verifyValidStudent(student) && studentService.deleteStudent(student)) {
                consolePrinter.print(messages.printStudentRemovedSuccess);
                LOGGER.debug("Student is removed.");
            }
        } catch (InvalidIdException | InputMismatchException e) {
            LOGGER.error("Failed to remove student: {}", e.getMessage());
        }
    }

    public void addStudentToCourse() {
        LOGGER.info("Run method: addStudentToCourse");
        try {
            consolePrinter.print(messages.inputStudentIdToAddToCourse);
            Student student = inputHandler.getStudent();
            LOGGER.debug("Received student ID: {}", student.getId());

            if (inputHandler.verifyValidStudent(student)) {
                consolePrinter.print(messages.inputCourseId);
                consolePrinter.printAllCourses();
                StudentAtCourse studentAtCourse = new StudentAtCourse(student, inputHandler.getCourse());
                LOGGER.debug("Received course Id: {}", studentAtCourse.getCourse().getId());

                if (studentService.addStudentToCourse(studentAtCourse)) {
                    consolePrinter.print(messages.printStudentAddedToCourseSuccess);
                    LOGGER.debug("Student added to course.");
                }
            }
        } catch (InvalidIdException | InputMismatchException | DuplicateKeyException e) {
            LOGGER.error("Failed adding student to course: {}.", e.getMessage());
        }
    }

    public void removeStudentFromCourse() {
        LOGGER.info("Run method: removeStudentFromCourse");
        try {
            consolePrinter.print(messages.inputStudentIdToRemoveFromCourse);
            Student student = studentService.getStudent(inputHandler.getStudent());
            LOGGER.debug("Received student id: {}.", student.getId());
            List<StudentAtCourse> allStudentCourses = studentService.getAllCoursesOfStudent(student);

            if (!CollectionUtils.isEmpty(allStudentCourses) && inputHandler.verifyValidStudent(student)) {
                consolePrinter.print(messages.chooseEnrollmentIdToRemove);
                consolePrinter.viewAllCoursesOfStudent(allStudentCourses);
                StudentAtCourse enrollmentID = inputHandler.getEnrollment();
                enrollmentID.setStudent(student);

                LOGGER.debug("Received enrollment id: {}.", enrollmentID.getId());

                if (studentService.removeStudentFromCourse(enrollmentID)) {
                    consolePrinter.print(messages.printStudentRemovedFromCourseSuccess);
                    LOGGER.debug("Student removed from course.");
                }
            } else {
                consolePrinter.print(messages.printStudentNotEnrolledInAnyCourse);
                LOGGER.debug("Student has not any course.");
            }
        } catch (InvalidIdException | InputMismatchException e) {
            LOGGER.error("Removing student from course is failed: {}", e.getMessage());
        }
    }
}
