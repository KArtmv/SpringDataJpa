package ua.foxminded.javaspring.consoleMenu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class Group extends BaseItem {

    @Column(nullable = false, length = 10)
    private String groupName;

    public Group() {
    }

    public Group(Long id) {
        super(id);
    }

    public Group(Long id, String groupName) {
        super(id);
        this.groupName = groupName;
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
