package com.bedtime.stories.repository;

import com.bedtime.stories.model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long>{

    Genre findByType(String type);
}
