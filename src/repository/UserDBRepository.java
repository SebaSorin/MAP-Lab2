package repository;

import domain.User;
import domain.validators.Validator;

/**
 * Skeleton of the database repository for users. The connection parameters
 * are stored, but no operation is implemented yet (laboratory 4).
 */
public class UserDBRepository implements Repository<Integer, User> {
    private static final String NOT_IMPLEMENTED = "Persistenta prin baza de date nu a fost implementata inca!";

    private final Validator<User> validator;
    private final String url;
    private final String user;
    private final String password;

    /**
     * Creates the repository.
     *
     * @param validator validator for users
     * @param url       database url
     * @param user      database user
     * @param password  database password
     */
    public UserDBRepository(Validator<User> validator, String url, String user, String password) {
        this.validator = validator;
        this.url = url;
        this.user = user;
        this.password = password;
    }


    @Override
    public User findOne(Integer integer) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    @Override
    public Iterable<User> findAll() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    @Override
    public User save(User entity) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    @Override
    public User delete(Integer integer) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    @Override
    public User update(User entity) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }
}
