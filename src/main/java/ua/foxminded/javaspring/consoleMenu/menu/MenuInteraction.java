package ua.foxminded.javaspring.consoleMenu.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.javaspring.consoleMenu.controller.CourseController;
import ua.foxminded.javaspring.consoleMenu.controller.GroupController;
import ua.foxminded.javaspring.consoleMenu.controller.StudentController;
import ua.foxminded.javaspring.consoleMenu.util.console.input.InputHandler;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

import javax.annotation.PostConstruct;

public class MenuInteraction {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuInteraction.class);

    private InputHandler consoleInput;
    private StudentController studentController;
    private CourseController courseController;
    private GroupController groupController;
    private ConsolePrinter consolePrinter;

    private boolean isExit;
    private static final String OPTION_EXIT = "x";

    @Autowired
    public MenuInteraction(InputHandler consoleInput, StudentController studentController, CourseController courseController, GroupController groupController, ConsolePrinter consolePrinter) {
        this.consoleInput = consoleInput;
        this.studentController = studentController;
        this.courseController = courseController;
        this.groupController = groupController;
        this.consolePrinter = consolePrinter;
    }

    @PostConstruct
    public void startMenu() {
        do {
            consolePrinter.printMenu();
            chooseOption();
        } while (!isExit);
    }

    private void chooseOption() {
        String receivedOption = consoleInput.inputOptionMenu();
        switch (receivedOption) {
            case "1":
                groupController.counterStudentsAtGroups();
                break;
            case "2":
                courseController.allStudentsFromCourse();
                break;
            case "3":
                studentController.addNewStudent();
                break;
            case "4":
                studentController.deleteStudent();
                break;
            case "5":
                studentController.addStudentToCourse();
                break;
            case "6":
                studentController.removeStudentFromCourse();
                break;
            case OPTION_EXIT:
                isExit = true;
                break;
            default:
                LOGGER.error("Invalid option selected: {}", receivedOption);
                break;
        }
    }
}
