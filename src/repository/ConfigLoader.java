package repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigLoader {

    /**
     * Loads the given properties resource (UTF-8).
     *
     * @param resourceName name of the resource, for example {@code config.properties}
     * @return the loaded properties
     * @throws RepositoryException if the resource is missing or cannot be read
     */
    public static Properties load(String resourceName){
        Properties props = new Properties();
        try (InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null)
                throw new RepositoryException("Fisierul de configuratie nu a fost gasit");
            try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)){
                props.load(reader);
            }
        } catch (IOException e){
            throw new RepositoryException("Nu s-a putut citi fisierul de configuratie", e);
        }
        return props;
    }
}
