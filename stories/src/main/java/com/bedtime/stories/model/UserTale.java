package com.bedtime.stories.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "UserTale")
@Table(name = "user_tale")
public class UserTale {

    @EmbeddedId
    private UserTaleId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("taleId")
    private Tale tale;

    @Column(name = "created_on")
    private Date createdOn = new Date();

    private UserTale() {}

    public UserTale(User user, Tale tale) {
        this.user = user;
        this.tale = tale;
        this.id = new UserTaleId(user.getId(), tale.getId());
    }

    //Getters and setters omitted for brevity

    public UserTaleId getId() {
        return id;
    }

    public void setId(UserTaleId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tale getTale() {
        return tale;
    }

    public void setTale(Tale tale) {
        this.tale = tale;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserTale that = (UserTale) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(tale, that.tale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, tale);
    }
}