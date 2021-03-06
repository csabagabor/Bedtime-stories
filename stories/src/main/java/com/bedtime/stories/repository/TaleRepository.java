package com.bedtime.stories.repository;


import com.bedtime.stories.model.Author;
import com.bedtime.stories.model.Genre;
import com.bedtime.stories.model.Tale;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Csabi on 9/11/2018.
 */
public interface TaleRepository extends CrudRepository<Tale, Long> {
    Tale getTaleByDateAdded(String dateAdded);

    Tale getTaleById(Long id);

    List<Tale> findTalesByDateAdded(String dateAdded);

    List<Tale> findTop25ByOrderByRatingDesc();

    List<Tale> findByAuthorAndGenre(Author author, Genre genre);

    List<Tale> findByAuthor(Author author);

    List<Tale> findByGenre(Genre genre);
}
