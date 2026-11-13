package repository;

import domain.Entity;
import domain.validators.ValidationException;
import domain.validators.Validator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory repository whose content is also stored in a text file, one entity
 * per line. The data is loaded at startup and the whole file is rewritten after
 * every successful {@code save}, {@code delete} or {@code update}.
 * Subclasses only define how an entity is converted to and from a line
 * (Template Method pattern).
 *
 * @param <ID> type of the identifier of the entities
 * @param <E>  type of the stored entities
 */
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {

    private final String fileName;
    private final List<String> loadWarnings = new ArrayList<>();

    /**
     * Creates the repository and loads the existing data. A missing file means
     * an empty repository. Invalid lines are skipped and reported through
     * {@link #getLoadWarnings()}.
     *
     * @param fileName  relative path of the data file
     * @param validator validator applied to every entity, including those read from the file
     * @throws RepositoryException if the file exists but cannot be read
     */
    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * @return a copy of the messages describing the lines ignored during loading
     *         (line number and reason)
     */
    @Override
    public List<String> getLoadWarnings(){
        return loadWarnings;
    }

    /**
     * Converts a line of the file into an entity. It is called from the
     * constructor, so it must not use fields of the subclass.
     *
     * @param line a non-blank line of the file
     * @return the entity described by the line
     * @throws RepositoryException if the line cannot be parsed
     */
    protected abstract E extractEntity(String line);


    private void loadData(){
        Path path = Paths.get(fileName);
        if (!Files.exists(path)){
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)){
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null){
                lineNumber++;
                if (line.isBlank())
                    continue;

                try {
                    E entity = extractEntity(line);
                    validator.validate(entity);
                    if (super.save(entity) != null)
                        loadWarnings.add("Linia " + lineNumber + " ignorata(id-ul este folosit deja)");
                } catch (RepositoryException | ValidationException e){
                    loadWarnings.add("Linia " + lineNumber + "ignorata(" + e.getMessage() + ")");
                }
            }
        } catch (IOException e){
            throw new RepositoryException("Nu s-a putut citi din fisierul " + fileName, e);
        }
    }

    /**
     * Converts an entity into the line that represents it in the file.
     *
     * @param entity the entity to convert
     * @return the line, without a line terminator
     */
    protected abstract String createEntityAsString(E entity);

    private void writeToFile(){
        Path path = Paths.get(fileName);
        try{
            Path parent = path.getParent();
            if (parent != null)
                Files.createDirectories(parent);

            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
                for (E entity : findAll()){
                    writer.write(createEntityAsString(entity));
                    writer.newLine();
                }
            }
        } catch (IOException e){
            throw new RepositoryException("Nu se poate scrie in fisierul " + fileName, e);
        }
    }

    /**
     * Saves the entity and, if it was saved, rewrites the file.
     *
     * @param entity the entity to store; must not be null
     * @return {@code null} if the entity was saved, otherwise the given entity
     * @throws IllegalArgumentException if the entity is null
     * @throws ValidationException      if the entity is invalid
     * @throws RepositoryException      if the file cannot be written
     */
    @Override
    public E save(E entity){
        E result = super.save(entity);
        if (result == null)
            writeToFile();
        return result;
    }

    /**
     * Deletes the entity and, if one was deleted, rewrites the file.
     *
     * @param id the id of the entity to delete; must not be null
     * @return the deleted entity, or {@code null} if there is none with that id
     * @throws IllegalArgumentException if the id is null
     * @throws RepositoryException      if the file cannot be written
     */
    @Override
    public E delete(ID id){
        E oldEntity = super.delete(id);
        if (oldEntity != null)
            writeToFile();
        return oldEntity;
    }

    /**
     * Replaces the entity and, if one was replaced, rewrites the file.
     *
     * @param entity the new version of the entity; must not be null
     * @return the replaced entity, or {@code null} if there is none with that id
     * @throws IllegalArgumentException if the entity is null
     * @throws ValidationException      if the entity is invalid
     * @throws RepositoryException      if the file cannot be written
     */
    @Override
    public E update(E entity){
        E oldEntity = super.update(entity);
        if (oldEntity != null)
            writeToFile();
        return oldEntity;
    }


}




