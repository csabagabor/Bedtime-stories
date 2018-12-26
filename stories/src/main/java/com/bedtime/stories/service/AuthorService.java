package com.bedtime.stories.service;

import com.bedtime.stories.model.Author;

import java.util.List;

public interface AuthorService {
    Author getAuthorByName(String name);
    List<Author> findAll();
}
