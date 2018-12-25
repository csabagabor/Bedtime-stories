package com.bedtime.stories.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type;

    @JsonIgnoreProperties("genre")
    @OneToMany(mappedBy="genre")
    private Set<Tale> tales;

    public Genre(){

    }

    public Genre(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Tale> getTales() {
        return tales;
    }

    public void setTales(Set<Tale> tales) {
        this.tales = tales;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", tales=" + tales +
                '}';
    }
}
