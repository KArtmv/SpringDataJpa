package ua.foxminded.javaspring.consoleMenu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.service.GroupService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;
import ua.foxminded.javaspring.consoleMenu.util.console.input.InputHandler;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

import java.util.InputMismatchException;
import java.util.List;

@Controller
public class GroupController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);
    private GroupService groupService;
    private InputHandler inputHandler;
    private ConsolePrinter consolePrinter;
    private ApplicationMessages messages;

    @Autowired
    public GroupController(GroupService groupService, InputHandler inputHandler, ConsolePrinter consolePrinter, ApplicationMessages messages) {
        this.groupService = groupService;
        this.inputHandler = inputHandler;
        this.consolePrinter = consolePrinter;
        this.messages = messages;
    }

    public void counterStudentsAtGroups() {
        LOGGER.info("Run method: counterStudentsAtGroups");
        try {
            consolePrinter.print(messages.inputGroupSizeToFindGroups);
            int requestedAmountOfStudent = inputHandler.getRequiredAmountOfStudents();
            List<CounterStudentsAtGroup> studentsAtGroups = groupService.counterStudentsAtGroups(requestedAmountOfStudent);
            LOGGER.debug("Received request to find less or equals amount of students by Groups: {}", requestedAmountOfStudent);

            if (CollectionUtils.isEmpty(studentsAtGroups)) {
                consolePrinter.print(String.format(messages.printNoGroupWithSize, requestedAmountOfStudent));
            } else {
                studentsAtGroups.sort((a, b) -> Long.compare(b.getStudentsCount(), a.getStudentsCount()));
                consolePrinter.viewAmountStudentAtGroup(studentsAtGroups);

                LOGGER.debug("Successfully received amount of students by groups.");
            }
        } catch (InputMismatchException e) {
            LOGGER.error("Error getting numbers: {}", e.getMessage());
        }
    }
}
