package com.bedtime.stories.service;

import com.bedtime.stories.model.User;
import com.bedtime.stories.model.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(long id);
    User findByUsername(String username);
    User findByEmail(String email);
    User findById(Long id);
}
