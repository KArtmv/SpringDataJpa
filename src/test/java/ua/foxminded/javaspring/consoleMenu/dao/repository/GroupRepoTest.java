package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import ua.foxminded.javaspring.consoleMenu.TestData;
import ua.foxminded.javaspring.consoleMenu.dao.GroupDAO;
import ua.foxminded.javaspring.consoleMenu.model.Group;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {GroupDAO.class}))
@ActiveProfiles("test")
@Sql({"/sql/group/groupTable.sql", "/sql/group/groupData.sql"})
class GroupRepoTest {

    private final TestData testData = new TestData();

    @Autowired
    private GroupDAO groupDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(statements = "INSERT INTO groups (group_name) values ('YS-28')")
    void findById_shouldReturnGroup_whenGivenIdIsExist() {
        Group group = testData.getGroup();
        assertThat(groupDAO.findById(group.getId()).get()).usingRecursiveComparison().isEqualTo(group);
    }

    @Test
    void findById_shouldReturnOptionalEmpty_whenGivenIdIsNotExist() {
        Group group = testData.getGroup();
        assertThat(groupDAO.findById(group.getId())).isNotPresent();
    }

    @Test
    void findAll_shouldReturnListOfAvailableGroups_whenItRequest() {
        assertThat(groupDAO.findAll()).hasSize(3);
    }

    @Test
    @Sql({"/sql/course/studentAndGroupTable.sql", "/sql/course/StudentAtGroupData.sql"})
    void counterStudentsAtGroups_shouldReturnListOfAmountStudentsByGroups_whenIsRun(){
        assertThat(groupDAO.counterStudentsAtGroups(6L)).usingRecursiveComparison().isEqualTo(testData.getStudentsAtGroupList());
    }
}