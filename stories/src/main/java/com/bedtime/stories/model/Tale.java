package com.bedtime.stories.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/*
 select * from TaleRating;
*/

/*
CREATE TABLE TaleRating (
  id              SERIAL PRIMARY KEY,
  date_added         date NOT NULL,
  rating REAL NOT NULL,
  nr_rating INT NOT NULL
);
 */
/*
INSERT INTO TaleRating (date_added , rating, nr_rating)
VALUES ('2018-09-16', 0, 0);
 */
/*
INSERT INTO TaleRating (date_added , rating, nr_rating)
SELECT date::date, 0, 0
from generate_series(
  '2018-09-16'::date,
  '2018-09-20'::date,
  '1 day'::interval
) date;
 */
@Entity
@Table(name = "tale")
public class Tale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JsonIgnoreProperties("tales")
    @JoinColumn(name="author_id", nullable=false)
    private Author author;

    @ManyToOne
    @JsonIgnoreProperties("tales")
    @JoinColumn(name="genre_id", nullable=false)
    private Genre genre;

    @Column
    private String description;

    @Column
    private int nrRating;
    @Column
    private float rating;


    private String dateAdded;


    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            },
            mappedBy = "tales")
    @JsonIgnoreProperties("tales")
    private Set<User> users = new HashSet<>();

    public Tale() {

    }


    public Tale(String title, Author author, Genre genre, String description, int nrRating, float rating, String dateAdded) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.description = description;
        this.nrRating = nrRating;
        this.rating = rating;
        this.dateAdded = dateAdded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNrRating() {
        return nrRating;
    }

    public void setNrRating(int nrRating) {
        this.nrRating = nrRating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    @JsonIgnore
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tale tale = (Tale) o;
        return nrRating == tale.nrRating &&
                Float.compare(tale.rating, rating) == 0 &&
                Objects.equals(id, tale.id) &&
                Objects.equals(title, tale.title) &&
                Objects.equals(author, tale.author) &&
                Objects.equals(genre, tale.genre) &&
                Objects.equals(description, tale.description) &&
                Objects.equals(dateAdded, tale.dateAdded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, genre, description, nrRating, rating, dateAdded);
    }

    @Override
    public String toString() {
        return "Tale{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", description='" + description + '\'' +
                ", nrRating=" + nrRating +
                ", rating=" + rating +
                ", dateAdded='" + dateAdded + '\'' +
                '}';
    }
}
