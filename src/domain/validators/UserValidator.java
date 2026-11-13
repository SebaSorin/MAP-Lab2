package domain.validators;

import domain.User;

/**
 * Validates User objects
 */
public class UserValidator implements Validator<User> {
    /**
     * Validates the given user
     * @param entity user reference
     * @throws UserValidationException if
     *          - the user's name is empty/null
     *          - the user's id is <= 0
     */
    @Override
    public void validate(User entity) {
        if (entity.getFirstName().isEmpty() || entity.getFirstName() == null || entity.getFirstName().trim().isEmpty())
            throw new UserValidationException("Numele nu poate fi gol");
        if (entity.getId() <= 0)
            throw new UserValidationException("Id-ul poate fi doar un numar natural nenul");
    }
}
