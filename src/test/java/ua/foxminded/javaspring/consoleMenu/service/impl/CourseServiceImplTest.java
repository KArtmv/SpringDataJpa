package ua.foxminded.javaspring.consoleMenu.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.javaspring.consoleMenu.DataInitializer;
import ua.foxminded.javaspring.consoleMenu.dao.CourseDAO;
import ua.foxminded.javaspring.consoleMenu.dao.StudentAtCourseDAO;
import ua.foxminded.javaspring.consoleMenu.exception.InvalidIdException;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseServiceImplTest {

    @Mock
    private StudentAtCourseDAO studentAtCourseDAO;
    @Mock
    private CourseDAO courseDAO;

    public static final Course course = new Course(1L);

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void allStudentsFromCourse_shouldReturnListOfStudentsToEnrollmentWithCourse_whenCourseIsExist() {
        List<StudentAtCourse> expect = new ArrayList<>();
        expect.add(new StudentAtCourse(new Student("firstName1", "lastName1"), course));
        expect.add(new StudentAtCourse(new Student("firstName2", "lastName2"), course));
        expect.add(new StudentAtCourse(new Student("firstName3", "lastName3"), course));

        when(courseDAO.getItemByID(anyLong())).thenReturn(Optional.of(course));
        when(studentAtCourseDAO.allStudentsFromCourse(course)).thenReturn(expect);

        assertThat(courseService.allStudentsFromCourse(course)).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expect);

        verify(courseDAO).getItemByID(course.getId());
        verify(studentAtCourseDAO).allStudentsFromCourse(course);
    }

    @Test
    void allStudentsFromCourse_shouldThrowsException_whenCourseIDIsNotExist() {
        when(courseDAO.getItemByID(anyLong())).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () ->
                courseService.allStudentsFromCourse(course));

        verify(courseDAO).getItemByID(course.getId());
    }

    @Test
    void getAllCourses_shouldReturnListOfCourses_whenRequest() {
        List<Course> courses = new DataInitializer().coursesListInit();

        when(courseDAO.getAll()).thenReturn(courses);

        assertThat(courseService.getAllCourses()).isEqualTo(courses);

        verify(courseDAO).getAll();
    }
}
