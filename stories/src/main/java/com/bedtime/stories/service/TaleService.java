package com.bedtime.stories.service;


import com.bedtime.stories.model.Rating;
import com.bedtime.stories.model.Tale;
import com.bedtime.stories.model.TaleDto;

import java.util.List;

/**
 * Created by Csabi on 9/11/2018.
 */
public interface TaleService {
    Tale getTaleByDate(String date);
    Tale addRatingByDate(String username, String date, int rating) throws Exception;
    Tale updateRatingByDate(String username, String date, int rating, int oldRating) throws Exception;


    List<Tale> getTopTales();

    Tale saveTale(TaleDto taleDto);

    Tale updateTale(String date, TaleDto taleDto);

    void deleteTaleByDate(String username, String date);

    List<Tale> getPostedTales();

    void deleteTaleById(Long id);

    Tale updateTaleById(Long id, String date);

    List<String> getAllAvailableDates();
    List<String> getAllFullDates();

    Rating getRatingByDate(String username, String date);

    List<Tale> getSearchTales(String author, String genre, String rating);
}
