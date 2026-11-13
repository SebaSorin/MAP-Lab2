import domain.Friendship;
import domain.Tuple;
import domain.User;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import repository.*;
import service.NetworkService;
import ui.Console;

import java.util.Properties;

public class Main {

    private static void ServicePopulation(NetworkService service){
        // Comunitate 1
        service.addUser(1, "a", "a");
        service.addUser(2, "b", "b");
        service.addUser(3, "c", "c");
        service.addUser(4, "d", "d");
        service.addUser(5, "e", "e");

        service.addFriendship(1, 2);
        service.addFriendship(2, 3);
        service.addFriendship(3, 4);
        service.addFriendship(4, 5);

        // Comunitate 2
        service.addUser(6, "f", "f");
        service.addUser(7, "g", "g");
        service.addUser(8, "h", "h");

        service.addFriendship(6, 7);
        service.addFriendship(7, 8);
        service.addFriendship(6, 8);

        // Coumnitate 3
        service.addUser(9, "i", "i");
    }


    public static void main(String[] args) {
        try {
            Properties config = ConfigLoader.load("config.properties");
            RepositoryFactory factory = new RepositoryFactory(config);

            Repository<Integer, User> userRepo =
                    factory.createUserRepository(new UserValidator());
            Repository<Tuple<Integer, Integer>, Friendship> friendshipRepo =
                    factory.createFriendshipRepository(new FriendshipValidator());

            NetworkService service = new NetworkService(userRepo, friendshipRepo);
            Console console = new Console(service);
            //ServicePopulation(service);
            console.run();
        } catch (RepositoryException e) {
            System.out.println("Eroare la pornire: " + e.getMessage());
        } catch (UnsupportedOperationException e) {
            System.out.println("Tipul de persistenta ales nu este implementat inca: " + e.getMessage());
        }
    }
}
