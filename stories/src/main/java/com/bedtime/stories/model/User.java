package com.bedtime.stories.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;


@Entity(name = "User")
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    @JsonIgnore
    private String passwordHash;


    @OneToMany(
            mappedBy = "user",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    private List<UserTale> talesRatings = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "user_roles",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    @JsonIgnoreProperties("users")
    private Set<Role> roles = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "user_tales",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "tale_id") })
    @JsonIgnoreProperties("users")
    private Set<Tale> tales = new HashSet<>();

    public User() {

    }

    public User(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }


    public void addTaleRatings(Tale tale) {
        UserTale userTale = new UserTale(this, tale);
        talesRatings.add(userTale);
        tale.getUsersRatings().add(userTale);
    }

    public void removeTaleRatings(Tale tale) {
        for (Iterator<UserTale> iterator = talesRatings.iterator();
             iterator.hasNext(); ) {
            UserTale userTale = iterator.next();

            if (userTale.getUser().equals(this) &&
                    userTale.getTale().equals(tale)) {
                iterator.remove();
                userTale.getTale().getUsersRatings().remove(userTale);
                userTale.setUser(null);
                userTale.setTale(null);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Tale> getTales() {
        return tales;
    }

    public void setTales(Set<Tale> tales) {
        this.tales = tales;
    }

    public List<UserTale> getTalesRatings() {
        return talesRatings;
    }

    public void setTalesRatings(List<UserTale> talesRatings) {
        this.talesRatings = talesRatings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(passwordHash, user.passwordHash) &&
                Objects.equals(talesRatings, user.talesRatings) &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(tales, user.tales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, passwordHash, talesRatings, roles, tales);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", talesRatings=" + talesRatings +
                ", roles=" + roles +
                ", tales=" + tales +
                '}';
    }
}
