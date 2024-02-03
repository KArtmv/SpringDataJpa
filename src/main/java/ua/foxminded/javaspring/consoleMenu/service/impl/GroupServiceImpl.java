package ua.foxminded.javaspring.consoleMenu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.consoleMenu.dao.GroupDAO;
import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.service.GroupService;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private GroupDAO groupDAO;

    @Autowired
    public GroupServiceImpl(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    public List<CounterStudentsAtGroup> counterStudentsAtGroups(Integer requestedAmountOfStudent) {
        return groupDAO.counterStudentsAtGroups(requestedAmountOfStudent);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupDAO.getAll();
    }
}
