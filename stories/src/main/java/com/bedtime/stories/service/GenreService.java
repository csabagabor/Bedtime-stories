package com.bedtime.stories.service;

import com.bedtime.stories.model.Genre;

import java.util.List;

public interface GenreService {
    Genre getGenreByType(String type);
    List<Genre> findAll();
}
