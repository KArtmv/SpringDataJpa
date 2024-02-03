package ua.foxminded.javaspring.consoleMenu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class Course extends BaseItem {

    @Column(nullable = false, length = 50)
    private String courseName;
    @Column(nullable = false, length = 100)
    private String courseDescription;

    public Course() {
    }

    public Course(Long id) {
        super(id);
    }

    public Course(String courseName, String courseDescription) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public Course(Long id, String courseName, String courseDescription) {
        super(id);
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

}
