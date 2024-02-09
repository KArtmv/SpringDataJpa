package ua.foxminded.javaspring.consoleMenu.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.javaspring.consoleMenu.ItemInstance;
import ua.foxminded.javaspring.consoleMenu.dao.GroupDAO;
import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.model.Group;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GroupServiceImplTest {

    private final ItemInstance instance = new ItemInstance();

    @Mock
    private GroupDAO groupDAO;
    @InjectMocks
    private GroupServiceImpl groupService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void counterStudentsAtGroups_shouldReturnListOfCountStudentsAtGroup_whenIsCalled() {
        List<CounterStudentsAtGroup> counterStudentsAtGroup = instance.getStudentsAtGroups();
        int countStudentsAtGroup = 22;

        when(groupDAO.counterStudentsAtGroups(anyLong())).thenReturn(counterStudentsAtGroup);

        assertThat(groupService.counterStudentsAtGroups(countStudentsAtGroup)).usingRecursiveComparison().isSameAs(counterStudentsAtGroup);

        verify(groupDAO).counterStudentsAtGroups((long) countStudentsAtGroup);
    }

    @Test
    void getAllGroups_shouldReturnListAvailableGroups_whenInvoke() {
        List<Group> groups = instance.getGroupList();

        when(groupDAO.findAll()).thenReturn(groups);

        assertThat(groupService.getAllGroups()).isSameAs(groups);

        verify(groupDAO).findAll();
    }
}
