package repository;

import domain.Friendship;
import domain.Tuple;
import domain.validators.Validator;

/**
 * Skeleton of the database repository for friendships. The connection
 * parameters are stored, but no operation is implemented yet (laboratory 4).
 */
public class FriendshipDBRepository implements Repository<Tuple<Integer, Integer>, Friendship> {
    private static final String NOT_IMPLEMENTED = "Persistenta prin baza de date nu a fost implementata inca!";

    private final Validator<Friendship> validator;
    private final String url;
    private final String user;
    private final String password;

    /**
     * Creates the repository.
     *
     * @param validator validator for friendships
     * @param url       database url
     * @param user      database user
     * @param password  database password
     */
    public FriendshipDBRepository(Validator<Friendship> validator, String url, String user, String password) {
        this.validator = validator;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Friendship findOne(Tuple<Integer, Integer> integerIntegerTuple) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    @Override
    public Iterable<Friendship> findAll() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    @Override
    public Friendship save(Friendship entity) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    @Override
    public Friendship delete(Tuple<Integer, Integer> integerIntegerTuple) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    @Override
    public Friendship update(Friendship entity) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }
}
