package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.consoleMenu.dao.GroupDAO;
import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.model.Group;

import javax.persistence.Query;
import java.util.List;

@Repository
public class GroupRepo extends GenericDAOWithJPA<Group, Long> implements GroupDAO {

    private static final String SQL_COUNT_STUDENTS_BY_GROUPS =
            "SELECT NEW ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup(g.groupName, COUNT(s))\n"
                    + "FROM Group g\n"
                    + "LEFT JOIN Student s ON g = s.group\n"
                    + "GROUP BY g\n"
                    + "HAVING COUNT(s) <= :count";

    @Override
    public List<CounterStudentsAtGroup> counterStudentsAtGroups(Integer count) {
        Query query = entityManager.createQuery(SQL_COUNT_STUDENTS_BY_GROUPS);
        query.setParameter("count", count.longValue());
        return query.getResultList();
    }
}
