package domain;

/**
 * Entity extension that has two User ids(Integer) as its ID
 * The id with the lower values is always on the left
 */
public class Friendship extends Entity<Tuple<Integer, Integer>> {

    public Friendship(int id1, int id2) {
        super(new Tuple<>(Math.min(id1, id2), Math.max(id1, id2)));
    }

    public int getLeftUser() {
        return getId().getLeft();
    }

    public int getRightUser() {
        return getId().getRight();
    }

    @Override
    public String toString() {
        return "User-ul cu id-ul " + getLeftUser() + " este prieten cu user-ul " + getRightUser();
    }
}
