package ua.foxminded.javaspring.consoleMenu.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface DAO<T, ID extends Serializable> {
    boolean addItem(T item);

    Optional<T> getItemByID(ID t);

    List<T> getAll();
}
