package com.bedtime.stories.repository;

import com.bedtime.stories.model.Rating;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {

    Rating findByUserIdAndTaleId(Long userId, Long taleId);
}
