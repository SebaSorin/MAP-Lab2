package domain.validators;

/**
 * Validates entities of the given type
 * @param <T> the entity type
 */
public interface Validator<T> {
    void validate(T entity);
}
