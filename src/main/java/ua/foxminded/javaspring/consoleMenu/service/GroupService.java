package ua.foxminded.javaspring.consoleMenu.service;

import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.model.Group;

import java.util.List;

@Service
public interface GroupService {
    List<CounterStudentsAtGroup> counterStudentsAtGroups(Integer requestedAmountOfStudent);

    List<Group> getAllGroups();
}
