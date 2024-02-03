package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.consoleMenu.dao.CourseDAO;
import ua.foxminded.javaspring.consoleMenu.model.Course;

@Repository
public class CourseRepo extends GenericDAOWithJPA<Course, Long> implements CourseDAO {

}
