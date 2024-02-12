package ua.foxminded.javaspring.consoleMenu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.consoleMenu.model.Student;

@Repository
public interface StudentDAO extends JpaRepository<Student, Long> {
}
