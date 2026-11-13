package domain;

import java.time.LocalDateTime;

/**
 * Entity extension that has two User ids(Integer) as its ID
 * The id with the lower values is always on the left
 */
public class Friendship extends Entity<Tuple<Integer, Integer>> {

    private final LocalDateTime date;


    public Friendship(int id1, int id2){
        super(new Tuple<>(Math.min(id1, id2), Math.max(id1, id2)));
        this.date = LocalDateTime.now();
    }

    public Friendship(int id1, int id2, LocalDateTime data) {
        super(new Tuple<>(Math.min(id1, id2), Math.max(id1, id2)));
        this.date = data;
    }

    public int getLeftUser() {
        return getId().getLeft();
    }

    public int getRightUser() {
        return getId().getRight();
    }

    @Override
    public String toString() {
        return "User-ul cu id-ul " + getLeftUser() + " este prieten cu user-ul " + getRightUser() +
                " data: " + date;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
