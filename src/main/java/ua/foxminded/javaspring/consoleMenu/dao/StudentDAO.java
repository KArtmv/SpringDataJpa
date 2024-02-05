package ua.foxminded.javaspring.consoleMenu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import java.util.List;

@Repository
public interface StudentDAO extends JpaRepository<Student, Long> {
}
