package com.bedtime.stories.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserTaleId
        implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "tale_id")
    private Long taleId;

    private UserTaleId() {}

    public UserTaleId(
            Long userId,
            Long taleId) {
        this.userId = userId;
        this.taleId = taleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaleId() {
        return taleId;
    }

    public void setTaleId(Long taleId) {
        this.taleId = taleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserTaleId that = (UserTaleId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(taleId, that.taleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, taleId);
    }
}