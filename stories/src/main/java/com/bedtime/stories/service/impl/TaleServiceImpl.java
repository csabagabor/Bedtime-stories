package com.bedtime.stories.service.impl;

import com.bedtime.stories.model.Tale;
import com.bedtime.stories.repository.TaleRepository;
import com.bedtime.stories.service.TaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaleServiceImpl implements TaleService {


    @Autowired
    private TaleRepository taleRepository;

    @Override
    public Tale getTaleByDate(String date) {
        return taleRepository.getTaleByDateAdded(date);
    }

    @Override
    public Float getRatingByDateAdded(String date) {
        return taleRepository.getRatingByDateAdded(date);
    }

    @Override
    public Tale addRatingByDate(String date, int rating) throws Exception {
        if (rating < 1 || rating > 5)
            throw new Exception("Rating must be between 1 and 5");
        Tale tale = taleRepository.getTaleByDateAdded(date);
        float overallRating = tale.getRating();
        int nrRating = tale.getNrRating();
        float newRating = calculateRating(overallRating, nrRating, rating);
        nrRating++;
        tale.setRating(newRating);
        tale.setNrRating(nrRating);
        return taleRepository.save(tale);
    }

    @Override
    public Tale updateRatingByDate(String date, int rating, int oldRating) throws Exception {

        if (rating < 1 || rating > 5)
            throw new Exception("Rating must be between 1 and 5");
        Tale tale = taleRepository.getTaleByDateAdded(date);
        float overallRating = tale.getRating();
        int nrRating = tale.getNrRating();
        if (nrRating < 1)//cannot update rating if no rating is present
            throw new Exception("No rating was given so far");
        float newRating = calculateRating(overallRating, nrRating, rating, oldRating);
        tale.setRating(newRating);
        return taleRepository.save(tale);
    }

//    @Override
//    public List<Tale> getTopTales(int limit) {
//        return taleRepository.getTopTales(limit);
//    }


    private float calculateRating(float overallRating, int nrRating, int rating) {
        float newRating = (overallRating * nrRating + rating) / (nrRating + 1);
        return newRating;
    }

    private float calculateRating(float overallRating, int nrRating, int rating, int oldRating) {
        float newRating = (overallRating * nrRating - oldRating + rating) / nrRating;
        return newRating;
    }
}
