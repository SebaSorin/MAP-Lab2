package repository;

import domain.User;
import domain.validators.Validator;

public class UserFileRepository extends AbstractFileRepository<Integer, User> {

    /**
     * Creates the repository and loads the users from the given file.
     *
     * @param fileName  relative path of the users file
     * @param validator validator applied to every user
     * @throws RepositoryException if the file exists but cannot be read
     */
    public UserFileRepository(String fileName, Validator<User> validator) {
        super(fileName, validator);
    }

    private static final String SEPARATOR = ";";

    /**
     * Parses a line of the form {@code id;firstName;lastName}.
     *
     * @param line a non-blank line of the file
     * @return the user described by the line
     * @throws RepositoryException if the line does not have 3 fields or the id is not a number
     */
    @Override
    protected User extractEntity(String line) {
        String[] fields = line.split(SEPARATOR, -1);
        if (fields.length != 3)
            throw new RepositoryException("Input invalid! Formatul este: id;prenume;nume");

        try{
            int id = Integer.parseInt(fields[0].trim());
            return new User(id, fields[1].trim(), fields[2].trim());
        } catch (NumberFormatException e){
            throw new RepositoryException("id invalid!", e);
        }
    }

    /**
     * @param entity the user to convert
     * @return the line {@code id;firstName;lastName}
     */
    @Override
    protected String createEntityAsString(User entity) {
        return entity.getId() + SEPARATOR + entity.getFirstName() + SEPARATOR + entity.getLastName();
    }
}
