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
    void findById_shouldReturnEnrollmentItem_whenIdIsExist() {
        StudentAtCourse studentAtCourse = instance.getStudentAtCourse();

        assertThat(studentAtCourseDAO.findById(studentAtCourse.getId()).get())
                .usingRecursiveComparison().isEqualTo(studentAtCourse);
    }

    @Test
    void allStudentsFromCourse_shouldReturnListOfStudentEnrollmentToCourse_whenRequest() {
        assertThat(studentAtCourseDAO.allStudentsFromCourse(instance.getCourse())).hasSize(3);
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(statements = "INSERT INTO studenttocourse (student_id, course_id) values (5, 1)")
    void delete_shouldRemoveEntryOfRelationshipBetweenStudentAndCourse_whenInvoke() {
        StudentAtCourse studentAtCourse = instance.getStudentAtCourse();

        assertAll(() -> {
            assertThat(studentAtCourseDAO.findById(studentAtCourse.getId())).isPresent();
            studentAtCourseDAO.delete(new StudentAtCourse(studentAtCourse.getId()));
            assertThat(studentAtCourseDAO.findById(studentAtCourse.getId())).isNotPresent();
        });
    }

    @Test
    void save_shouldSaveNewEnrollment_whenInvoke() {
        StudentAtCourse studentAtCourse = instance.getStudentAtCourse();

        assertAll(() -> {
            assertThat(studentAtCourseDAO.save(new StudentAtCourse(instance.getStudent(), instance.getCourse()))
                    .getId()).isNotNull();
            assertThat(studentAtCourseDAO.findById(studentAtCourse.getId()).get()).usingRecursiveComparison().isEqualTo(studentAtCourse);
        });
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(statements = "INSERT INTO studenttocourse (student_id, course_id) values (5, 1)")
    void findAllStudentCourses_shouldReturnListCoursesRelateToStudent_whenItRun() {
        assertThat(studentAtCourseDAO.findAllStudentCourses(instance.getStudent())).hasSize(2);
    }
}