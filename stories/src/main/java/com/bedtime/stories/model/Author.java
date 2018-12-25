package com.bedtime.stories.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private String name;

    @JsonIgnoreProperties("author")
    @OneToMany(mappedBy="author")
    private Set<Tale> tales;

    public Author(){

    }

    public Author(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Tale> getTales() {
        return tales;
    }

    public void setTales(Set<Tale> tales) {
        this.tales = tales;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tales=" + tales +
                '}';
    }
}
