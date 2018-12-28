package com.bedtime.stories.service.impl;

import com.bedtime.stories.model.Author;
import com.bedtime.stories.repository.AuthorRepository;
import com.bedtime.stories.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {


    @Autowired
    private AuthorRepository authorRepository;



    @Override
    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name);
    }

    @Override
    public List<Author> findAll() {
        return ((List<Author>) authorRepository.findAll()).
                stream().
                sorted(Comparator.comparing(Author::getName)).
                collect(Collectors.toList());
    }
}
