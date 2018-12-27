package com.bedtime.stories.model;

import javax.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long taleId;

    @Column
    private Integer rating;


    public Rating(){

    }

    public Rating(Long userId, Long taleId, Integer rating) {
        this.userId = userId;
        this.taleId = taleId;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
