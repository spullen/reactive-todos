package net.scottpullen.common.entities;

import net.scottpullen.tasks.entities.Task;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

import static net.scottpullen.common.ArgumentPreconditions.required;

public class EntityId {
    private final UUID id;

    public EntityId(UUID id) {
        required(id, "id required");

        this.id = id;
    }

    public EntityId(String id) {
        this(UUID.fromString(id));
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) {
            return false;
        } else if(other == this) {
            return true;
        } else if(!getClass().isAssignableFrom(other.getClass())) {
            return false;
        } else {
            EntityId otherEntityId = (EntityId) other;
            return new EqualsBuilder()
                .append(id, otherEntityId.getValue())
                .isEquals();
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .toHashCode();
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
