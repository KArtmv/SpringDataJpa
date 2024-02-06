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
@Sql({"/sql/student/studentTable.sql", "/sql/student/studentData.sql"})
class StudentRepoTest {

    private final ItemInstance instance = new ItemInstance();
    private final TestData testData = new TestData();

    @Autowired
    private StudentDAO studentDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(statements = "INSERT INTO students (first_name, last_name, group_id) values ('Ava', 'Rodriguez', 1)")
    void findById_shouldReturnStudent_whenIdIsExist() {
        Student student = testData.getStudent();

        assertThat(studentDAO.findById(student.getId()).get()).usingRecursiveComparison()
                .isEqualTo(student);
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenIdIsNotExist() {
        Student student = testData.getStudent();

        assertThat(studentDAO.findById(student.getId())).isNotPresent();
    }

    @Test
    void save_shouldSuccessfullySaveNewStudent_whenIsRun() {
        assertAll(() -> {
            assertThat(studentDAO.save(new Student(testData.studentFirstName, testData.studentLastName, instance.getGroup()))
                    .getId()).isNotNull();

            Student student = testData.getStudent();
            assertThat(studentDAO.findById(student.getId()).get()).usingRecursiveComparison().isEqualTo(student);
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