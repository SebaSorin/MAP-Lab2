package repository;

import domain.Entity;

/**
 * CRUD operations
 *
 * @param <ID> - type E has an id of type ID
 * @param <E>  - the type of entities saved in the repository
 */
public interface Repository<ID, E extends Entity<ID>> {
    /**
     * Returns the entity with the given id
     * @param id the entity's id must not be null
     * @return the entity with the given id
     * or null if there's no such entity
     * @throws IllegalArgumentException if the id is null
     */
    E findOne(ID id);

    /**
     * @return all the entities in the repo
     */
    Iterable<E> findAll();

    /**
     * Stores the given entity in the repo
     * @param entity object to be saved must not be null
     * @return null if the save was succesfully
     * otherwise returns the entity (id already exists)
     * @throws IllegalArgumentException if the entity is null
     * @throws domain.validators.ValidationException if the entity is invalid
     */
    E save(E entity);

    /**
     * Deletes the entity with the given
     * @param id the entity's id must not be null
     * @return the deleted entity
     * otherwise returns null if there's no entity with that id
     * @throws IllegalArgumentException if the id is null
     */
    E delete(ID id);

    /**
     * Updates the value associated with the given entity's id
     * @param entity must not be null
     * @return null if there's no entity with the given id
     * otherwise returns the entity previously assosciated with the id
     * @throws IllegalArgumentException if the entity is null
     */
    E update(E entity);
}
