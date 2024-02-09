package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import ua.foxminded.javaspring.consoleMenu.ItemInstance;
import ua.foxminded.javaspring.consoleMenu.TestData;
import ua.foxminded.javaspring.consoleMenu.dao.StudentDAO;
import ua.foxminded.javaspring.consoleMenu.model.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {StudentDAO.class}))
@ActiveProfiles("test")
@Sql({"/sql/tables.sql", "/sql/student/studentData.sql"})
class StudentRepoTest {

    private final ItemInstance instance = new ItemInstance();
    private final TestData testData = new TestData();

    @Autowired
    private StudentDAO studentDAO;

    @Test
    void findById_shouldReturnStudent_whenIdIsExist() {
        assertThat(studentDAO.findById(instance.getStudentId().getId()).get()).usingRecursiveComparison()
                .isEqualTo(instance.getStudent());
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenIdIsNotExist() {
        assertThat(studentDAO.findById(6L)).isNotPresent();
    }

    @Test
    void save_shouldSuccessfullySaveNewStudent_whenIsRun() {
        assertAll(() -> {
            assertThat(studentDAO.save(new Student(testData.studentFirstName, testData.studentLastName, instance.getGroup()))
                    .getId()).isNotNull();

            assertThat(studentDAO.findById(6L).get()).usingRecursiveComparison().isEqualTo(instance.getExpectedStudent());
        });
    }

    @Test
    void delete_shouldSuccessfullyRemoveStudent_whenItRun() {
        Student student = instance.getStudent();

        assertAll(() -> {
            assertThat(studentDAO.findById(student.getId())).isPresent();
            studentDAO.delete(student);
            assertThat(studentDAO.findById(student.getId())).isNotPresent();
        });
    }
}