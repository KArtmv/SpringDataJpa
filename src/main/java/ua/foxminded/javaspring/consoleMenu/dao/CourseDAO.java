package ua.foxminded.javaspring.consoleMenu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.consoleMenu.model.Course;

@Repository
public interface CourseDAO extends JpaRepository<Course, Long> {
}
