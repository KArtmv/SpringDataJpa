package ua.foxminded.javaspring.consoleMenu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import java.util.List;

@Repository
public interface StudentAtCourseDAO extends JpaRepository<StudentAtCourse, Long> {

    @Query("select c from StudentAtCourse c where c.course = ?1")
    List<StudentAtCourse> allStudentsFromCourse(Course course);

    @Query("select s from StudentAtCourse s where s.student = ?1")
    List<StudentAtCourse> findAllStudentCourses(Student student);
}
