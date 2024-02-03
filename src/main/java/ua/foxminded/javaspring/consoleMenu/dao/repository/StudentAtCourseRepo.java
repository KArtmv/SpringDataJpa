package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.javaspring.consoleMenu.dao.StudentAtCourseDAO;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class StudentAtCourseRepo extends GenericDAOWithJPA<StudentAtCourse, Long> implements StudentAtCourseDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentAtCourseRepo.class);

    @Override
    public List<StudentAtCourse> allStudentsFromCourse(Course course) {
        TypedQuery<StudentAtCourse> query = entityManager
                .createQuery("SELECT stc FROM StudentAtCourse stc WHERE stc.course=:course", StudentAtCourse.class)
                .setParameter("course", course);
        return query.getResultList();
    }

    @Override
    @Transactional
    public boolean removeStudentFromCourse(StudentAtCourse studentAtCourse) {
        try {
            entityManager.remove(entityManager.find(StudentAtCourse.class, studentAtCourse.getId()));
            return true;
        } catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Failed to remove student from course: {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public void removeStudentFromAllTheirCourses(Student student) {
        try {
            Query query = entityManager
                    .createQuery("DELETE FROM StudentAtCourse stc where student=:student")
                    .setParameter("student", student);
            query.executeUpdate();
        } catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Failed to remove student from all their courses: {}", e.getMessage());
        }
    }
}
