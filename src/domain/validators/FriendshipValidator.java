package domain.validators;

import domain.Friendship;

/**
 * Validates Friendship objects
 */
public class FriendshipValidator implements Validator<Friendship> {
    /**
     * Vaidates the given friendship
     * @param entity friendship reference
     * @throws FriendshipValidationException if
     *          - either the left id or the right id is <= 0
     *          - the ids have the same value
     */
    @Override
    public void validate(Friendship entity) {
        if (entity.getLeftUser() <= 0 || entity.getRightUser() <= 0)
            throw new FriendshipValidationException("Id-urile pot fi doar numare naturale nenule");
        if (entity.getLeftUser() == entity.getRightUser())
            throw new FriendshipValidationException("Un utilizator nu poate fi prieten cu el insusi!");
        if (entity.getDate() == null)
            throw new FriendshipValidationException("Data nu poate fi nula!");
    }
}
