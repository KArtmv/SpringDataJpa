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
import ua.foxminded.javaspring.consoleMenu.dao.CourseDAO;
import ua.foxminded.javaspring.consoleMenu.model.Course;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CourseDAO.class}))
@ActiveProfiles("test")
@Sql({"/sql/tables.sql", "/sql/course/courseData.sql"})
class CourseRepoTest {

    private final TestData testData = new TestData();
    private final ItemInstance instance = new ItemInstance();

    @Autowired
    private CourseDAO courseDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(statements = "INSERT INTO courses (course_name, course_description) values ('Introduction to Psychology', 'Learn the basics of psychology')")
    void getById_shouldReturnCourse_whenGivenIdIsExist() {
        assertThat(courseDAO.findById(instance.getCourseId().getId()).get()).usingRecursiveComparison().isEqualTo(instance.getCourse());
    }

    @Test
    void findById_shouldReturnOptionalEmpty_whenGivenIdIsNotExist() {
        assertThat(courseDAO.findById(4L)).isNotPresent();
    }

    @Test
    void findAll_shouldReturnListOfAvailableCourses_whenItRequest() {
        assertThat(courseDAO.findAll()).hasSize(3);
    }
}

