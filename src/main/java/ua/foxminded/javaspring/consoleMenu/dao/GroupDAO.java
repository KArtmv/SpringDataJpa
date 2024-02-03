package ua.foxminded.javaspring.consoleMenu.dao;

import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.model.Group;

import java.util.List;

public interface GroupDAO extends DAO<Group, Long> {
    List<CounterStudentsAtGroup> counterStudentsAtGroups(Integer count);

}
