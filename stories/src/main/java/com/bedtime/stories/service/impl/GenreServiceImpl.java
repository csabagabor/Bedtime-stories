package com.bedtime.stories.service.impl;

import com.bedtime.stories.model.Genre;
import com.bedtime.stories.repository.GenreRepository;
import com.bedtime.stories.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GenreServiceImpl implements GenreService {


    @Autowired
    private GenreRepository genreRepository;


    @Override
    public Genre getGenreByType(String type) {
        return genreRepository.findByType(type);
    }

    @Override
    public List<Genre> findAll() {
        return (List<Genre>) genreRepository.findAll();
    }
}
