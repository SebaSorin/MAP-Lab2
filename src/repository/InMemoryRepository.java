package repository;

import domain.Entity;
import domain.validators.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository that keeps the entities in a map, in memory. Every entity is
 * validated before it is saved or updated.
 *
 * @param <ID> type of the identifier of the entities
 * @param <E>  type of the stored entities
 */
public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    private final Map<ID, E> entities = new HashMap<>();
    protected final Validator<E> validator;

    /**
     * {@inheritDoc}
     */
    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E findOne(ID id) {
        if (id == null)
            throw new IllegalArgumentException("Id-ul nu poate fi null!");
        return entities.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> findAll() {
        return Collections.unmodifiableCollection(entities.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E save(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entitatea nu poate fi null!");

        validator.validate(entity);
        if (entities.putIfAbsent(entity.getId(), entity) == null)
            return null;
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E delete(ID id) {
        if (id == null)
            throw new IllegalArgumentException("Id-ul nu poate fi null!");

        return entities.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E update(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entitatea nu poate fi null!");

        validator.validate(entity);
        if (entities.replace(entity.getId(), entity) == null)
            return entity;
        return null;
    }
}
