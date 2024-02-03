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
    void getById_shouldReturnStudent_whenIdIsExist() {
        Student student = testData.getStudent();

        assertThat(studentDAO.getItemByID(student.getId()).get()).usingRecursiveComparison()
                .isEqualTo(student);
    }

    @Test
    void getById_shouldReturnEmptyOptional_whenIdIsNotExist() {
        Student student = testData.getStudent();

        assertThat(studentDAO.getItemByID(student.getId())).isNotPresent();
    }

    @Test
    void addItem_shouldSuccessfullySaveNewStudent_whenIsRun() {
        assertAll(() -> {
            assertThat(studentDAO.addItem(new Student(testData.studentFirstName, testData.studentLastName, instance.getGroup()))).isTrue();

            Student student = testData.getStudent();
            assertThat(studentDAO.getItemByID(student.getId()).get()).usingRecursiveComparison().isEqualTo(student);
        });
    }

    @Test
    void removeStudent_shouldSuccessfullyRemoveStudent_whenItRun() {
        Student student = instance.getStudent();

        assertAll(() -> {
            assertThat(studentDAO.getItemByID(student.getId())).isPresent();
            assertThat(studentDAO.removeStudent(student)).isTrue();
            assertThat(studentDAO.getItemByID(student.getId())).isNotPresent();
        });
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.OVERRIDE)
    @Sql({"/sql/studentAtCourse/tables.sql", "/sql/studentAtCourse/data.sql"})
    @Sql(statements = "INSERT INTO studenttocourse (student_id, course_id) values (5, 1)")
    void studentCourses_shouldReturnListCoursesRelateToStudent_whenItRun() {
        assertThat(studentDAO.studentCourses(instance.getStudent())).hasSize(2);
    }
}