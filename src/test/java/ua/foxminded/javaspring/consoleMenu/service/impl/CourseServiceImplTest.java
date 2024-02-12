package ua.foxminded.javaspring.consoleMenu.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.javaspring.consoleMenu.ItemInstance;
import ua.foxminded.javaspring.consoleMenu.dao.CourseDAO;
import ua.foxminded.javaspring.consoleMenu.exception.InvalidIdException;
import ua.foxminded.javaspring.consoleMenu.model.Course;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseServiceImplTest {

    private final ItemInstance instance = new ItemInstance();
    public Course course;

    @Mock
    private CourseDAO courseDAO;
    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        course = instance.getCourse();
    }

    @Test
    void getCourse_shouldReturnListOfStudentsToEnrollmentWithCourse_whenCourseIsExist() {
        when(courseDAO.findById(anyLong())).thenReturn(Optional.of(course));

        assertThat(courseService.getCourse(instance.getCourseId())).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(course);

        verify(courseDAO).findById(course.getId());
    }

    @Test
    void getCourse_shouldThrowsException_whenCourseIDIsNotExist() {
        when(courseDAO.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> courseService.getCourse(instance.getCourseId()));

        verify(courseDAO).findById(course.getId());
    }

    @Test
    void getAllCourses_shouldReturnListOfCourses_whenRequest() {
        when(courseDAO.findAll()).thenReturn(instance.getCoursesList());

        assertThat(courseService.getAllCourses()).hasSize(3);

        verify(courseDAO).findAll();
    }
}
