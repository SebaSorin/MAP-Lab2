package domain;

import java.util.Objects;


/**
 * Abstract class for entities that have an id
 * @param <ID> abstract id
 */
public class Entity<ID> {
    private ID id;

    public Entity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Entity<?> otherEntity))
            return false;
        return Objects.equals(this.id, otherEntity.id);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id);
    }

}
