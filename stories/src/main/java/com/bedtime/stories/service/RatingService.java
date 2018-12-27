package com.bedtime.stories.service;

import com.bedtime.stories.model.Rating;

public interface RatingService {
    Rating findByUserIdAndTaleId(Long userId, Long taleId);
}
