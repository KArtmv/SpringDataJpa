package ua.foxminded.javaspring.consoleMenu.model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    public BaseItem() {
    }

    public BaseItem(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
