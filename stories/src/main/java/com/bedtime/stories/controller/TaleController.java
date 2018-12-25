package com.bedtime.stories.controller;

import com.bedtime.stories.model.Tale;
import com.bedtime.stories.service.TaleService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.monitorjbl.json.JsonViewModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
        Float ratingNode = taleService.getRatingByDateAdded(date);
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("rating", ratingNode);
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

//    @GetMapping(value = "/top/{limit}", produces = "application/json")
//    public String getTopTales(@PathVariable int limit) throws JsonProcessingException {
//
//        List<Tale> list = taleService.getTopTales(limit);
//        String json = mapper.writeValueAsString(JsonView.with(list).onClass(Tale.class, match().include("dateAdded")));
//        return json;
//    }


}