package com.bedtime.stories.controller;

import com.bedtime.stories.model.Tale;
import com.bedtime.stories.model.TaleDto;
import com.bedtime.stories.service.TaleService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.monitorjbl.json.JsonViewModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tale")
public class TaleController {

    @Autowired
    private TaleService taleService;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JsonViewModule());

    @GetMapping("/{date}")
    public Tale getTaleByDate(@PathVariable String date) {
        return taleService.getTaleByDate(date);
    }

    @GetMapping("/")
    public Tale getTodaysTale() {
        return taleService.getTaleByDate(LocalDate.now().toString());
    }

    @GetMapping(value = "/rating/{date}", produces = "application/json")
    public ObjectNode getRatingByDate(@PathVariable String date) {
        Tale tale = taleService.getTaleByDate(date);
        Float rating = tale.getRating();
        Integer nrRating = tale.getNrRating();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("rating", rating);
        objectNode.put("nr_rating", nrRating);
        return objectNode;
    }


    @PutMapping(value = "/rating/{date}", produces = "application/json")
    public Tale updateRatingByDate(@PathVariable String date,
                                   @RequestBody String body) throws Exception {
        JsonNode jsonNode = mapper.readTree(body);
        int rating = jsonNode.get("rating").intValue();
        int oldRating = jsonNode.get("oldRating").intValue();
        return taleService.updateRatingByDate(date, rating, oldRating);
    }

    @PostMapping(value = "/rating/{date}", produces = "application/json")
    public Tale addRatingByDate(@PathVariable String date,
                                @RequestBody String body) throws Exception {
        JsonNode jsonNode = mapper.readTree(body);
        int rating = jsonNode.get("rating").intValue();
        return taleService.addRatingByDate(date, rating);
    }

    @GetMapping(value = "/top/{limit}", produces = "application/json")
    public List<Tale> getTopTales(@PathVariable int limit) {
        //limit is 25 currently, can be changed later if there is need
        return taleService.getTopTales();
    }

    @GetMapping(value = "/posted", produces = "application/json")
    public List<Tale> getPostedTales() {
        return taleService.getPostedTales();
    }

    @GetMapping(value = "/dates", produces = "application/json")
    public List<String> getAllAvaialbleDates() {
        return taleService.getAllAvailableDates();
    }

    @GetMapping(value = "/dates/full", produces = "application/json")
    public List<String> getAllFullDates() {
        return taleService.getAllFullDates();
    }


    @PostMapping(value = "/tales", produces = "application/json")
    public Tale addTale(@Valid @RequestBody TaleDto taleDto) throws Exception {
        return taleService.saveTale(taleDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/tales/{date}", produces = "application/json")
    public Tale modifyTale(@PathVariable String date, @Valid @RequestBody TaleDto taleDto) throws Exception {
        return taleService.updateTale(date, taleDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/tales/{date}", produces = "application/json")
    public ObjectNode deleteTale(@PathVariable String date) throws Exception {
        taleService.deleteTaleByDate(date);
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("success", "true");
        return objectNode;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/tales/id/{id}", produces = "application/json")
    public ObjectNode deleteTaleById(@PathVariable Long id) throws Exception {
        taleService.deleteTaleById(id);
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("success", "true");
        return objectNode;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/tales/id/{id}", produces = "application/json")
    public Tale updateTaleById(@PathVariable Long id, @RequestBody Map<String, String> date) throws Exception {
        String dateAdded = date.get("date");
        return taleService.updateTaleById(id,dateAdded );
    }

}