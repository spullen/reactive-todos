package net.scottpullen.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;

public class User {

    private final UserId id;
    private final String email;
    private final String fullName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public User(final UserId id, final String email, final String fullName, final LocalDateTime createdAt,
                final LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UserId getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
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
            User otherUser = (User) other;
            return new EqualsBuilder()
                .append(id, otherUser.getId())
                .append(email, otherUser.getEmail())
                .append(fullName, otherUser.getFullName())
                .append(createdAt, otherUser.getCreatedAt())
                .append(updatedAt, otherUser.getUpdatedAt())
                .isEquals();
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .append(email)
            .append(fullName)
            .append(createdAt)
            .append(updatedAt)
            .toHashCode();
    }

    @Override
    public String toString() {
        return "User(id: " + id.toString() + ", email: " + email + ", fullName: " + fullName
            + ", createdAt: " + createdAt + ", updatedAt: " + updatedAt + ")";
    }
}
