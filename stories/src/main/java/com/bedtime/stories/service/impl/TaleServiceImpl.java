package com.bedtime.stories.service.impl;

import com.bedtime.stories.model.*;
import com.bedtime.stories.repository.AuthorRepository;
import com.bedtime.stories.repository.GenreRepository;
import com.bedtime.stories.repository.TaleRepository;
import com.bedtime.stories.repository.UserRepository;
import com.bedtime.stories.service.TaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaleServiceImpl implements TaleService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaleRepository taleRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public Tale getTaleByDate(String date) {
        return taleRepository.getTaleByDateAdded(date);
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
        //
        User user = userRepository.findByUsername("user1");
        user.addTaleRatings(tale);
        taleRepository.save(tale);

        userRepository.save(user);

        //
        return tale;
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

    @Override
    public List<Tale> getTopTales() {
        return taleRepository.findTop25ByOrderByRatingDesc();
    }


    private float calculateRating(float overallRating, int nrRating, int rating) {
        float newRating = (overallRating * nrRating + rating) / (nrRating + 1);
        return newRating;
    }

    private float calculateRating(float overallRating, int nrRating, int rating, int oldRating) {
        float newRating = (overallRating * nrRating - oldRating + rating) / nrRating;
        return newRating;
    }

    @Override
    public Tale saveTale(TaleDto taleDto) {
        Genre genre = genreRepository.findByType(taleDto.getGenre());
        Author author = authorRepository.findByName(taleDto.getAuthor());
        Tale tale = new Tale(taleDto.getTitle(), author, genre, taleDto.getDescription(),
                0, 0.0f, "");
        genre.getTales().add(tale);
        author.getTales().add(tale);
        return taleRepository.save(tale);
    }

    @Override
    public Tale updateTale(String date, TaleDto taleDto) {
        Tale tale = taleRepository.getTaleByDateAdded(date);

        tale.getAuthor().getTales().remove(tale);
        tale.getGenre().getTales().remove(tale);

        Author author = authorRepository.findByName(taleDto.getAuthor());
        Genre genre = genreRepository.findByType(taleDto.getGenre());
        tale.setAuthor(author);
        tale.setGenre(genre);
        author.getTales().add(tale);
        genre.getTales().add(tale);

        tale.setTitle(taleDto.getTitle());
        tale.setDescription(taleDto.getDescription());

        return taleRepository.save(tale);
    }

    @Override
    public void deleteTaleByDate(String date) {
        Tale tale = taleRepository.getTaleByDateAdded(date);
        taleRepository.delete(tale);
    }

    @Override
    public List<Tale> getPostedTales() {
        return taleRepository.findTalesByDateAdded("");
    }

    @Override
    public void deleteTaleById(Long id) {
        Tale tale = taleRepository.getTaleById(id);
        taleRepository.delete(tale);
    }

    @Override
    public Tale updateTaleById(Long id, String date) {
        Tale tale = taleRepository.getTaleById(id);
        tale.setDateAdded(date);
        return taleRepository.save(tale);
    }

    @Override
    public List<String> getAllAvailableDates() {
        List<String> fillDates = getAllFullDates();
        List<String> availableDates = new ArrayList<>();

        for (LocalDate date = LocalDate.of(2018, 11, 10);
             date.isBefore(LocalDate.of(2019, 1, 29)); date = date.plusDays(1)) {
            if (!fillDates.contains(date.toString())) {
                availableDates.add(date.toString());
            }
        }
        return availableDates;
    }

    @Override
    public List<String> getAllFullDates() {
        LocalDate dt = LocalDate.parse("2005-11-12");
        List<Tale> tales = (List<Tale>) taleRepository.findAll();
        return tales.stream().map(t -> LocalDate.parse(t.getDateAdded()))
                .sorted().map(LocalDate::toString)
                .collect(Collectors.toList());
    }
}
