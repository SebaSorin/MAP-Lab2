package repository;

import domain.Friendship;
import domain.Tuple;
import domain.User;
import domain.validators.Validator;

import java.util.Locale;
import java.util.Properties;


public class RepositoryFactory {

    private final Properties config;
    private final RepositoryType type;

    /**
     * Creates the factory. The persistence type is read only once, so both
     * repositories use the same type.
     *
     * @param config application settings
     * @throws RepositoryException if {@code repository.type} is missing or unknown
     */
    public RepositoryFactory(Properties config) {
        this.config = config;
        this.type = parseType(getRequired("repository.type"));
    }


    private String getRequired(String key){
        String value = config.getProperty(key);
        if (value == null || value.isBlank())
            throw new RepositoryException("Missing property: " + key);
        return value.trim();
    }

    private static RepositoryType parseType(String value){
        try{
            return RepositoryType.valueOf(value.trim().toUpperCase());
        } catch(IllegalArgumentException e){
            throw new RepositoryException("Tip repo invalid (tipuri valide: MEMORY, FILE, DATABASE)", e);
        }
    }

    /**
     * Creates the users repository for the configured persistence type.
     *
     * @param validator validator for users
     * @return the users repository
     * @throws RepositoryException if a required property is missing or the
     *                             data file cannot be read
     */
    public Repository<Integer, User> createUserRepository(Validator<User> validator){
        switch(type){
            case MEMORY:
                return new InMemoryRepository<>(validator);
            case FILE:
                return new UserFileRepository(getRequired("repository.users.file"), validator);
            case DATABASE:
                return new UserDBRepository(validator,
                        getRequired("db.url"), getRequired("db.user"), getRequired("db.password"));
            default:
                throw new RepositoryException("Tip persistenta invalid!");
        }
    }

    /**
     * Creates the friendships repository for the configured persistence type.
     *
     * @param validator validator for friendships
     * @return the friendships repository
     * @throws RepositoryException if a required property is missing or the
     *                             data file cannot be read
     */
    public Repository<Tuple<Integer, Integer>, Friendship> createFriendshipRepository(Validator<Friendship> validator) {
        switch (type){
            case MEMORY:
                return new InMemoryRepository<>(validator);
            case FILE:
                return new FriendshipFileRepository(getRequired("repository.friendship.file"), validator);
            case DATABASE:
                return new FriendshipDBRepository(validator,
                        getRequired("db.url"), getRequired("db.user"), getRequired("db.password"));
            default:
                throw new RepositoryException("Tip de persistenta invalid!");
        }
    }


}
