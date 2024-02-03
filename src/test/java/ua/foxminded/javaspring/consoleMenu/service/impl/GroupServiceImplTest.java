package ua.foxminded.javaspring.consoleMenu.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.javaspring.consoleMenu.DataInitializer;
import ua.foxminded.javaspring.consoleMenu.dao.GroupDAO;
import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.model.Group;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GroupServiceImplTest {

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
        List<CounterStudentsAtGroup> counterStudentsAtGroup = new ArrayList<>();
        counterStudentsAtGroup.add(new CounterStudentsAtGroup("someGroup1", 22L));
        counterStudentsAtGroup.add(new CounterStudentsAtGroup("someGroup2", 18L));
        counterStudentsAtGroup.add(new CounterStudentsAtGroup("someGroup3", 10L));

        int countStudentsAtGroup = 22;

        when(groupDAO.counterStudentsAtGroups(anyInt())).thenReturn(counterStudentsAtGroup);

        List<CounterStudentsAtGroup> result = groupService.counterStudentsAtGroups(countStudentsAtGroup);

        assertThat(result).usingRecursiveComparison().isSameAs(counterStudentsAtGroup);

        verify(groupDAO).counterStudentsAtGroups(anyInt());
    }

    @Test
    void getAllGroups_shouldReturnListAvailableGroups_whenInvoke() {
        List<Group> groups = new DataInitializer().groupsListInit();

        when(groupDAO.getAll()).thenReturn(groups);

        assertThat(groupService.getAllGroups()).isSameAs(groups);

        verify(groupDAO).getAll();
    }
}
