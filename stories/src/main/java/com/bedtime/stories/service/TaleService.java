package com.bedtime.stories.service;


import com.bedtime.stories.model.Tale;

/**
 * Created by Csabi on 9/11/2018.
 */
public interface TaleService {
    Tale getTaleByDate(String date);
    Float getRatingByDateAdded(String date);
    Tale addRatingByDate(String date, int rating) throws Exception;
    Tale updateRatingByDate(String date, int rating, int oldRating) throws Exception;
    //List<Tale> getTopTales(int limit);
}
