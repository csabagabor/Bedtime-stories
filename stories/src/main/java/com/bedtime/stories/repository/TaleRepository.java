package com.bedtime.stories.repository;


import com.bedtime.stories.model.Tale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Csabi on 9/11/2018.
 */
public interface TaleRepository extends CrudRepository<Tale, Long> {
    Tale getTaleByDateAdded(String dateAdded);

    @Query("SELECT r.rating FROM Tale r where r.dateAdded = :date")
    Float getRatingByDateAdded(@Param("date") String dateAdded);

    //List<Tale> getTopTales(int limit);
}
