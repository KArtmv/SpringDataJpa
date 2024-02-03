package ua.foxminded.javaspring.consoleMenu.model;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student extends BaseItem {

    @Column(nullable = false, length = 15)
    private String firstName;
    @Column(nullable = false, length = 15)
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    public Student() {
    }

    public Student(Long id) {
        super(id);
    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(String firstName, String lastName, Group group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public Student(Long id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(Long id, String firstName, String lastName, Group group) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
