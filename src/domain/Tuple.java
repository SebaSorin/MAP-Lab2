package domain;

import java.util.Objects;

/**
 * Contains two values, each of a given type
 * @param <E1> type of the left value
 * @param <E2> type of the right value
 */
public class Tuple<E1, E2> {
    private final E1 left;
    private final E2 right;

    public Tuple(E1 left, E2 right) {
        this.left = left;
        this.right = right;
    }

    public E1 getLeft() {
        return left;
    }

    public E2 getRight() {
        return right;
    }

    @Override
    public String toString(){
        return "(" + left.toString() + ", " + right.toString() + ")";
    }

    /**
     * Verifies if the given Object is the same
     * @param other   the reference object with which to compare.
     * @return true if
     *              - it's the same reference
     *              - the other object is a Tuple that contians the same values as this one
     *         false otherwise
     */
    @Override
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Tuple<?,?> otherTuple))
            return false;
        return Objects.equals(this.left, otherTuple.left) &&
                Objects.equals(this.right, otherTuple.right);
    }

    /**
     * @return the hashCode for this object
     * (used in Repositories for hashMaps)
     */
    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
