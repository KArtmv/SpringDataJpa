package ua.foxminded.javaspring.consoleMenu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import ua.foxminded.javaspring.consoleMenu.exception.InvalidIdException;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.service.CourseService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;
import ua.foxminded.javaspring.consoleMenu.util.console.input.InputHandler;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

import java.util.InputMismatchException;
import java.util.List;

@Controller
public class CourseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
    private CourseService courseService;
    private ConsolePrinter consolePrinter;
    private ApplicationMessages messages;
    private InputHandler inputHandler;

    @Autowired
    public CourseController(CourseService courseService, ConsolePrinter consolePrinter, ApplicationMessages messages, InputHandler inputHandler) {
        this.courseService = courseService;
        this.consolePrinter = consolePrinter;
        this.messages = messages;
        this.inputHandler = inputHandler;
    }

    public void allStudentsFromCourse() {
        LOGGER.info("Run allStudentsFromCourse method.");
        try {
            consolePrinter.printAllCourses();
            consolePrinter.print(messages.inputCourseIdToViewEnrolledStudents);
            Course course = inputHandler.getCourse();
            LOGGER.debug("Received course ID: {}", course.getId());
            List<StudentAtCourse> studentsFromCourse = courseService.allStudentsFromCourse(course);

            if (!CollectionUtils.isEmpty(studentsFromCourse)) {
                LOGGER.debug("The list of students on the course has been successfully compiled");
                consolePrinter.viewAllStudentsFromCourse(studentsFromCourse);
            } else {
                LOGGER.debug("No found students for the selected course.");
            }
        } catch (InvalidIdException | InputMismatchException e) {
            LOGGER.error("Failed to found the students: {}", e.getMessage());
        }
    }
}
