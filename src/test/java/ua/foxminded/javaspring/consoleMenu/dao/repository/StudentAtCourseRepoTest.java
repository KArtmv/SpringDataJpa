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
import ua.foxminded.javaspring.consoleMenu.dao.StudentAtCourseDAO;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = StudentAtCourseDAO.class))
@ActiveProfiles("test")
@Sql({"/sql/studentAtCourse/tables.sql", "/sql/studentAtCourse/data.sql"})
class StudentAtCourseRepoTest {

    private final ItemInstance instance = new ItemInstance();

    @Autowired
    private StudentAtCourseDAO studentAtCourseDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(statements = "INSERT INTO studenttocourse (student_id, course_id) values (5, 1)")
    void getById_shouldReturnEnrollmentItem_whenIdIsExist() {
        StudentAtCourse studentAtCourse = instance.getStudentAtCourse();

        assertThat(studentAtCourseDAO.getItemByID(studentAtCourse.getId()).get())
                .usingRecursiveComparison().isEqualTo(studentAtCourse);
    }

    @Test
    void allStudentsFromCourse_shouldReturnListOfStudentEnrollmentToCourse_whenRequest() {
        assertThat(studentAtCourseDAO.allStudentsFromCourse(instance.getCourse())).hasSize(3);
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(statements = "INSERT INTO studenttocourse (student_id, course_id) values (5, 1)")
    void removeStudentFromCourse_shouldRemoveEnrollment_whenInvoke() {
        StudentAtCourse studentAtCourse = instance.getStudentAtCourse();

        assertAll(() -> {
            assertThat(studentAtCourseDAO.getItemByID(studentAtCourse.getId())).isPresent();
            assertThat(studentAtCourseDAO.removeStudentFromCourse(new StudentAtCourse(studentAtCourse.getId()))).isTrue();
            assertThat(studentAtCourseDAO.getItemByID(studentAtCourse.getId())).isNotPresent();
        });
    }

    @Test
    void addItem_shouldSaveNewEnrollment_whenInvoke() {
        StudentAtCourse studentAtCourse = instance.getStudentAtCourse();

        assertAll(() -> {
            assertThat(studentAtCourseDAO.addItem(new StudentAtCourse(instance.getStudent(), instance.getCourse()))).isTrue();
            assertThat(studentAtCourseDAO.getItemByID(studentAtCourse.getId()).get()).usingRecursiveComparison().isEqualTo(studentAtCourse);
        });
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(statements = "INSERT INTO studenttocourse (student_id, course_id) values (5, 1)")
    void removeStudentFromAllTheirCourses_shouldRemoveAllEnrollmentsRelateToStudent_whenStudentHasCourse() {
        assertAll(() -> {
            assertThat(studentAtCourseDAO.getAll()).hasSize(11);

            studentAtCourseDAO.removeStudentFromAllTheirCourses(instance.getStudent());

            assertThat(studentAtCourseDAO.getAll()).hasSize(9);
        });
    }
}