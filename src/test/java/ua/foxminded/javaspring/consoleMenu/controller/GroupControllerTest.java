package ua.foxminded.javaspring.consoleMenu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import ua.foxminded.javaspring.consoleMenu.ItemInstance;
import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.service.GroupService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;
import ua.foxminded.javaspring.consoleMenu.util.console.input.InputHandler;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GroupControllerTest {

    private final ItemInstance instance = new ItemInstance();

    private List<CounterStudentsAtGroup> counterStudentsAtGroups;

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
        counterStudentsAtGroups = instance.getStudentsAtGroups();

        when(inputHandler.getRequiredAmountOfStudents()).thenReturn(requiredAmountOfStudent);
        when(groupService.counterStudentsAtGroups(anyInt())).thenReturn(counterStudentsAtGroups);

        groupController.counterStudentsAtGroups();

        verify(consolePrinter).print(messages.inputGroupSizeToFindGroups);
        verify(inputHandler).getRequiredAmountOfStudents();
        verify(groupService).counterStudentsAtGroups(requiredAmountOfStudent);
        verify(consolePrinter).viewAmountStudentAtGroup(counterStudentsAtGroups);
    }

    @Test
    void counterStudentsAtGroups_shouldDoNothing_whenNotFoundGroupsWithGivenStudentAmount() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ReflectionTestUtils.setField(messages, "printNoGroupWithSize", "Request amount: %s.");
        System.setOut(new PrintStream(outputStream));

        int requiredAmountOfStudent = 10;

        when(inputHandler.getRequiredAmountOfStudents()).thenReturn(requiredAmountOfStudent);
        when(groupService.counterStudentsAtGroups(anyInt())).thenReturn(Collections.emptyList());

        groupController.counterStudentsAtGroups();

        verify(consolePrinter).print(anyString());
    }



    @Test
    void counterStudentsAtGroups_shouldThrowInputMismatchException_whenInputNotNumericCharacter() {
        when(inputHandler.getRequiredAmountOfStudents()).thenThrow(new InputMismatchException());

        groupController.counterStudentsAtGroups();

        verify(consolePrinter).print(messages.printNoGroupWithSize);
        verify(inputHandler).getRequiredAmountOfStudents();
    }
}