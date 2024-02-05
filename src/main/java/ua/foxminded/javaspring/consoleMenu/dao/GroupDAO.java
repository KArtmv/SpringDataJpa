package ua.foxminded.javaspring.consoleMenu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.model.Group;

import java.util.List;

@Repository
public interface GroupDAO extends JpaRepository<Group, Long> {

    @Query("SELECT NEW ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup(g.groupName, COUNT(s))\n"
            + "FROM Group g\n"
            + "LEFT JOIN Student s ON g = s.group\n"
            + "GROUP BY g\n"
            + "HAVING COUNT(s) <= ?1")
    List<CounterStudentsAtGroup> counterStudentsAtGroups(Long count);

}
