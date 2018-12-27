package com.bedtime.stories.service.impl;


import com.bedtime.stories.model.Rating;
import com.bedtime.stories.repository.RatingRepository;
import com.bedtime.stories.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;


    @Override
    public Rating findByUserIdAndTaleId(Long userId, Long taleId) {
        return ratingRepository.findByUserIdAndTaleId(userId, taleId);
    }
}
