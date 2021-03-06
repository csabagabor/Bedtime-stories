package com.bedtime.stories.model;

import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class TaleDto {

    @NotEmpty
    @SafeHtml
    @Size(min=2, message="Title should have at least 2 characters")
    private String title;

    @NotEmpty
    @SafeHtml
    private String author;

    @NotEmpty
    @SafeHtml
    private String genre;

    @NotEmpty
    @SafeHtml
    @Size(min=20, message="Description should have at least 20 characters")
    private String description;

    @SafeHtml
    private String dateAdded;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
