package repository;

import domain.Friendship;
import domain.Tuple;
import domain.validators.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class FriendshipFileRepository extends
        AbstractFileRepository<Tuple<Integer, Integer>, Friendship> {

    private static final String SEPARATOR = ";";

    /**
     * Creates the repository and loads the friendships from the given file.
     *
     * @param fileName  relative path of the friendships file
     * @param validator validator applied to every friendship
     * @throws RepositoryException if the file exists but cannot be read
     */
    public FriendshipFileRepository(String fileName, Validator<Friendship> validator) {
        super(fileName, validator);
    }

    /**
     * Parses a line of the form {@code id1;id2;date}.
     *
     * @param line a non-blank line of the file
     * @return the friendship described by the line
     * @throws RepositoryException if the line does not have 3 fields, an id is
     *                             not a number or the date is not in ISO format
     */
    @Override
    protected Friendship extractEntity(String line) {
        String[] fields = line.split(SEPARATOR, -1);
        if (fields.length != 3)
            throw new RepositoryException("Input invalid! Formatul este id;id;data");

        try {
            int id1 = Integer.parseInt(fields[0].trim());
            int id2 = Integer.parseInt(fields[1].trim());
            LocalDateTime date = LocalDateTime.parse(fields[2].trim());
            return new Friendship(id1, id2, date);
        } catch (NumberFormatException e){
            throw new RepositoryException("id invalid!", e);
        } catch (DateTimeParseException e) {
            throw new RepositoryException("data invalida!", e);
        }
    }

    /**
     * @param entity the friendship to convert
     * @return the line {@code id1;id2;date}
     */
    @Override
    protected String createEntityAsString(Friendship entity) {
        return entity.getLeftUser() + SEPARATOR + entity.getRightUser() + SEPARATOR + entity.getDate();
    }
}
