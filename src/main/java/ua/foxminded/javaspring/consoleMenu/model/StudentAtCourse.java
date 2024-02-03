package ua.foxminded.javaspring.consoleMenu.model;

import javax.persistence.*;

@Entity
@Table(name = "studenttocourse",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
public class StudentAtCourse extends BaseItem {

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public StudentAtCourse() {
    }

    public StudentAtCourse(Long id) {
        super(id);
    }

    public StudentAtCourse(Long id, Student student) {
        super(id);
        this.student = student;
    }

    public StudentAtCourse(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public StudentAtCourse(Long id, Student student, Course course) {
        super(id);
        this.student = student;
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
