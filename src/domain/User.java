package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity extension that has an Integer as its ID
 * Contains a name for the user and the user's friend list
 */
public class User extends Entity<Integer> {
    private String name;
    private final List<Integer> friendList;

    public User(int id, String name) {
        super(id);
        this.name = name;
        this.friendList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "id: " + getId().toString() +
                " name: " + name +
                " friend ids: " + friendList;
    }


    public List<Integer> getFriendList() {
        return friendList;
    }

    /**
     * Adds an id to the friend list if the friendship doesn't exist yet
     * @param friendId = the id of the friend that gets added
     */
    public void addFriend(int friendId) {
        if (!friendList.contains(friendId))
            friendList.add(friendId);
    }

    /**
     * Removes a friend from the list
     * @param friendId = the id of the removed friend
     */
    public void removeFriend(Integer friendId){
        friendList.remove(friendId);
    }

}
