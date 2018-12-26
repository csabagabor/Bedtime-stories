package com.bedtime.stories.service.impl;

import com.bedtime.stories.exception.DuplicateEntityException;
import com.bedtime.stories.model.Role;
import com.bedtime.stories.model.Tale;
import com.bedtime.stories.model.User;
import com.bedtime.stories.model.UserDto;
import com.bedtime.stories.repository.RoleRepository;
import com.bedtime.stories.repository.TaleRepository;
import com.bedtime.stories.repository.UserRepository;
import com.bedtime.stories.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TaleRepository taleRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPasswordHash(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            //authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
        //return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User save(UserDto user) {
        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new DuplicateEntityException("Duplicate Email");

        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new DuplicateEntityException("Duplicate Username");

        User newUser = new User(user.getUsername(), user.getEmail(), bcryptEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("USER");
        newUser.getRoles().add(role);
        role.getUsers().add(newUser);
        return userRepository.save(newUser);
    }

    @Override
    public User changePassword(String username, String passwordOld, String password) throws Exception {
        User user = userRepository.findByUsername(username);
        if (!bcryptEncoder.matches(passwordOld, user.getPasswordHash()))
            throw new Exception("Invalid credentials!");
        user.setPasswordHash(bcryptEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User addFavoriteTale(String username, String dateAdded) {
        User user = userRepository.findByUsername(username);
        Tale tale = taleRepository.getTaleByDateAdded(dateAdded);
        user.getTales().add(tale);
        tale.getUsers().add(user);
        return userRepository.save(user);
    }

    @Override
    public List<String> getFavoritesDates(String username) {
        User user = userRepository.findByUsername(username);
        return user.getTales().stream().map(Tale::getDateAdded).collect(Collectors.toList());
    }

    @Override
    public User removeFavoriteTale(String username, String date) {
        User user = userRepository.findByUsername(username);
        Tale tale = taleRepository.getTaleByDateAdded(date);
        user.getTales().remove(tale);
        tale.getUsers().remove(user);
        return userRepository.save(user);
    }
}
