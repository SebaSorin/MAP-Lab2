package service;

import domain.Friendship;
import domain.Tuple;
import domain.User;
import repository.Repository;

import java.util.*;

public class NetworkService {
    private final Repository<Integer, User> userRepo;
    private final Repository<Tuple<Integer, Integer>, Friendship> friendshipRepo;

    public NetworkService(Repository<Integer, User> userRepo,
                          Repository<Tuple<Integer, Integer>, Friendship> friendshipRepo) {
        this.userRepo = userRepo;
        this.friendshipRepo = friendshipRepo;
    }

    public Repository<Integer, User> getUserRepo(){
        return userRepo;
    }

    public Repository<Tuple<Integer, Integer>, Friendship> getFriendshipRepo(){
        return friendshipRepo;
    }

    /**
     * Adds a user
     * @param id must be > 0 and not null
     * @param name must not be null
     * @throws ServiceException if id is already in use
     */
    public void addUser(int id, String name){
        User newUser = new User(id, name);
        if (userRepo.save(newUser) != null)
            throw new ServiceException("Acest id este folosit deja!");
    }

    /**
     * Removes a user (also removes the friendships that that user was part of)
     * @param id must be > 0 and not null
     * @throws ServiceException if the user doesn't exist
     * @throws IllegalArgumentException if id is null
     */
    public void removeUser(int id){
        if (userRepo.findOne(id) == null)
            throw new ServiceException("Nu exista user cu acest id!");

        List<Friendship> friendships = new ArrayList<>();
        for (Friendship fs : friendshipRepo.findAll()){
            friendships.add(fs);
        }

        for (Friendship fs : friendships){
            if (fs.getLeftUser() == id){
                int otherId = fs.getRightUser();
                userRepo.findOne(otherId).removeFriend(id);
                friendshipRepo.delete(new Tuple<>(id, otherId));
            }
            else if (fs.getRightUser() == id){
                int otherId = fs.getLeftUser();
                userRepo.findOne(otherId).removeFriend(id);
                friendshipRepo.delete(new Tuple<>(otherId, id));
            }
        }

        userRepo.delete(id);
    }

    /**
     * Adds friendship
     * @param id1 must be > 0 and not null
     * @param id2 must be > 0 and not null
     * @throws ServiceException if the ids have the same values,
     * either is null or if the friendship already exists
     */
    public void addFriendship(int id1, int id2){
        if (id1 == id2)
            throw new ServiceException("Un user nu poate fi prieten cu el insusi!");

        User user1 = userRepo.findOne(id1);
        User user2 = userRepo.findOne(id2);
        if (user1 == null || user2 == null)
            throw new ServiceException("Unul dintre sau ambii utilizatori sunt null");

        Friendship newFriendship = new Friendship(id1, id2);
        if (friendshipRepo.findOne(newFriendship.getId()) != null)
            throw new ServiceException("Prietenia exista deja!");

        friendshipRepo.save(newFriendship);
        user1.addFriend(id2);
        user2.addFriend(id1);
    }

    /**
     * Removes a friendship
     * @param id1 must be > 0 and not null
     * @param id2 must be > 0 and not null
     * @throws ServiceException if the ids have the same values,
     * either is null or if the friendship doesn't exist
     */
    public void removeFriendship(int id1, int id2){
        if (id1 == id2)
            throw new ServiceException("Un user nu poate fi prieten cu el insusi!");

        User user1 = userRepo.findOne(id1);
        User user2 = userRepo.findOne(id2);
        if (user1 == null || user2 == null)
            throw new ServiceException("Unul dintre sau ambii utilizatori sunt null");

        Friendship oldFriendship = friendshipRepo.delete(new Tuple<>(Math.min(id1, id2), Math.max(id1, id2)));
        if (oldFriendship == null)
            throw new ServiceException("Prietenia aceasta nu exista!");

        user1.removeFriend(id2);
        user2.removeFriend(id1);
    }

    /**
     * Creates a community based off all the linked friendships
     * @param id the current user
     * @param visited contains all the previously visited users.
     * @param community contains the ids of all the users within the community
     */
    private void dfs(int id, Set<Integer> visited, List<User> community){
        visited.add(id);
        User currentUser = userRepo.findOne(id);

        if (currentUser == null)
            return;

        community.add(currentUser);
        for (Integer idFriend : currentUser.getFriendList()){
            if (!visited.contains(idFriend))
                dfs(idFriend, visited, community);
        }
    }

    /**
     * @return all the communities of the current network
     * (a list of user lists)
     */
    public List<List<User>> getAllComunities(){
        Set<Integer> visited = new HashSet<>();
        List<List<User>> comunities = new ArrayList<>();

        for (User currentUser : userRepo.findAll()){
            if (!visited.contains(currentUser.getId())) {
                List<User> community = new ArrayList<User>();
                dfs(currentUser.getId(), visited, community);
                comunities.add(community);
            }
        }
        return comunities;
    }

    /**
     * @return the current number of communities
     */
    public int getComunityCount(){
        return getAllComunities().size();
    }

    /**
     * @param currentId the current user's id
     * @param localVisited set of the currently visited users
     * @return the longest chain of friendships starting from the given user
     */
    private int longestPathFromNode(int currentId, Set<Integer> localVisited){
        localVisited.add(currentId);
        int maxPath = 0;

        User currentUser = userRepo.findOne(currentId);
        if (currentUser != null){
            for (Integer idFriend : currentUser.getFriendList()){
                if (!localVisited.contains(idFriend)){
                    int currentPath = 1 + longestPathFromNode(idFriend, localVisited);
                    maxPath = Math.max(maxPath, currentPath);
                }
            }
        }

        localVisited.remove(currentId);
        return maxPath;
    }

    /**
     * @return the most sociable community
     * (the community with the longest chain of friendships)
     */
    public List<User> getMostSociableCommunity(){
        List<List<User>> communities = getAllComunities();
        List<User> mostSociable = new ArrayList<>();
        int maxDistance = -1;

        for (List<User> community : communities){
            int maxPathInCommunity = 0;

            for (User u : community){
                Set<Integer> localVisited = new HashSet<>();
                maxPathInCommunity = Math.max(maxPathInCommunity,
                                              longestPathFromNode(u.getId(), localVisited));
            }

            if (maxPathInCommunity > maxDistance) {
                maxDistance = maxPathInCommunity;
                mostSociable = community;
            }
        }

        return mostSociable;
    }

}
