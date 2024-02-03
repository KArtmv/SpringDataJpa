package ua.foxminded.javaspring.consoleMenu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.service.GroupService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;
import ua.foxminded.javaspring.consoleMenu.util.console.input.InputHandler;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GroupControllerTest {

    @Mock
    private GroupService groupService;
    @Mock
    private ConsolePrinter consolePrinter;
    @Mock
    private ApplicationMessages messages;
    @Mock
    private InputHandler inputHandler;
    @InjectMocks
    private GroupController groupController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void counterStudentsAtGroups_shouldViewGroupsByAmountOfStudents_whenFoundedByRequiredAmount() {
        int requiredAmountOfStudent = 10;
        List<CounterStudentsAtGroup> counterStudentsAtGroups = new ArrayList<>();
        counterStudentsAtGroups.add(new CounterStudentsAtGroup("group1", 10L));
        counterStudentsAtGroups.add(new CounterStudentsAtGroup("group2", 1L));
        counterStudentsAtGroups.add(new CounterStudentsAtGroup("group3", 5L));

        when(inputHandler.getRequiredAmountOfStudents()).thenReturn(requiredAmountOfStudent);
        when(groupService.counterStudentsAtGroups(anyInt())).thenReturn(counterStudentsAtGroups);

        groupController.counterStudentsAtGroups();

        verify(consolePrinter).print(messages.inputGroupSizeToFindGroups);
        verify(inputHandler).getRequiredAmountOfStudents();
        verify(groupService).counterStudentsAtGroups(requiredAmountOfStudent);
        verify(consolePrinter).viewAmountStudentAtGroup(counterStudentsAtGroups);
    }

    @Test
    void testCounterStudentsAtGroupsException() {
        when(inputHandler.getRequiredAmountOfStudents()).thenThrow(new InputMismatchException());

        groupController.counterStudentsAtGroups();

        verify(consolePrinter).print(messages.inputGroupSizeToFindGroups);
        verify(inputHandler).getRequiredAmountOfStudents();
    }
}