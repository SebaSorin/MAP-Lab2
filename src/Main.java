import domain.Friendship;
import domain.Tuple;
import domain.User;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import repository.InMemoryRepository;
import repository.Repository;
import service.NetworkService;
import ui.Console;

public class Main {
    public static void main(String[] args) {
        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        final Repository<Integer, User> userRepo = new InMemoryRepository<>(userValidator);
        final Repository<Tuple<Integer, Integer>, Friendship> friendshipRepo = new InMemoryRepository<>(friendshipValidator);
        NetworkService service = new NetworkService(userRepo, friendshipRepo);
        Console console = new Console(service);

        // Comunitate 1
        service.addUser(1, "a");
        service.addUser(2, "b");
        service.addUser(3, "c");
        service.addUser(4, "d");
        service.addUser(5, "e");

        service.addFriendship(1, 2);
        service.addFriendship(2, 3);
        service.addFriendship(3, 4);
        service.addFriendship(4, 5);

        // Comunitate 2
        service.addUser(6, "f");
        service.addUser(7, "g");
        service.addUser(8, "h");

        service.addFriendship(6, 7);
        service.addFriendship(7, 8);
        service.addFriendship(6, 8);

        // Coumnitate 3
        service.addUser(9, "i");

        console.run();
    }
}
