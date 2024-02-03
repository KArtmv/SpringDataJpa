package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class GenericDAOWithJPA<T, ID extends Serializable> {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Class<T> persistentClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    protected GenericDAOWithJPA() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Transactional
    public boolean addItem(T item) {
        try {
            entityManager.persist(item);
            return true;
        } catch (PersistenceException e) {
            LOGGER.error("Failed to persist item", e);
            return false;
        }
    }

    public Optional<T> getItemByID(ID id) {
        try {
            return Optional.ofNullable(entityManager.find(persistentClass, id));
        } catch (PersistenceException e) {
            LOGGER.error("Failed to obtain item by ID", e);
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return entityManager.createQuery("Select t from " + persistentClass.getSimpleName() + " t").getResultList();
    }
}
