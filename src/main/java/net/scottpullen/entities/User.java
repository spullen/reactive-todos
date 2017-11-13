package net.scottpullen.entities;

import java.time.LocalDateTime;

public class User {

    private final UserId userId;
    private final String email;
    private final String fullName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public User(final UserId userId, final String email, final String fullName, final LocalDateTime createdAt,
                final LocalDateTime updatedAt) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UserId getUserId() {
        return userId;
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
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if(!(o instanceof User)) {
            return false;
        }

        User otherUser = ((User) o);

        return otherUser.getUserId().getValue() == userId.getValue();
    }

    /* TODO
    @Override
    public int hashCode() {

    }
    */
}
