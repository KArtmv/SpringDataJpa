package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.javaspring.consoleMenu.dao.StudentDAO;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class StudentRepo extends GenericDAOWithJPA<Student, Long> implements StudentDAO {

    private final Logger LOGGER = LoggerFactory.getLogger(StudentRepo.class);

    @Override
    @Transactional
    public boolean removeStudent(Student student) {
        try {
            entityManager.remove(entityManager.find(Student.class, student.getId()));
            return true;
        } catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Failed to remove student: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public List<StudentAtCourse> studentCourses(Student student) {
        TypedQuery<StudentAtCourse> query = entityManager
                .createQuery("SELECT stc FROM StudentAtCourse stc WHERE stc.student=:student", StudentAtCourse.class)
                .setParameter("student", student);
        return query.getResultList();
    }
}
