package com.bedtime.stories.controller;

import com.bedtime.stories.config.TokenProvider;
import com.bedtime.stories.model.User;
import com.bedtime.stories.model.UserDto;
import com.bedtime.stories.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;


    @Autowired
    private UserService userService;

    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> listUser() {
        return userService.findAll();
    }

    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('USER')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getOne(@PathVariable(value = "id") Long id) {
        return userService.findById(id);
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public User saveUser(@Valid @RequestBody UserDto user) {
        User userResult = userService.save(user);
        return userResult;
    }

    @RequestMapping(value = "/favorite/{date}", method = RequestMethod.POST)
    public User addFavoriteTale(@RequestHeader HttpHeaders httpHeaders, @PathVariable String date) {
        String token = getTokenFromHeader(httpHeaders);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return userService.addFavoriteTale(username, date);
    }

    @RequestMapping(value = "/favorite/{date}", method = RequestMethod.DELETE)
    public User removeFavoriteTale(@RequestHeader HttpHeaders httpHeaders, @PathVariable String date) {
        String token = getTokenFromHeader(httpHeaders);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return userService.removeFavoriteTale(username, date);
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    public List<String> getFavoritesDates(@RequestHeader HttpHeaders httpHeaders) {
        String token = getTokenFromHeader(httpHeaders);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return userService.getFavoritesDates(username);
    }


    private String getTokenFromHeader(HttpHeaders httpHeaders) {
        Map<String, String> headerMap = httpHeaders.toSingleValueMap();
        String token = headerMap.get("authorization");
        token = token.replace("Bearer ", "");
        return token;
    }
}
