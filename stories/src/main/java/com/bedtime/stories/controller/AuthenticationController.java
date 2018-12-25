package com.bedtime.stories.controller;

import com.bedtime.stories.config.TokenProvider;
import com.bedtime.stories.exception.TokenExpirationException;
import com.bedtime.stories.model.AuthToken;
import com.bedtime.stories.model.LoginUser;
import com.bedtime.stories.model.User;
import com.bedtime.stories.model.UserDto;
import com.bedtime.stories.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.bedtime.stories.model.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.bedtime.stories.model.Constants.ACCESS_TOKEN_VALIDITY_SECONDS_REMEMBER_ME;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token;
        if (loginUser.getRememberMe())
            token = jwtTokenUtil.generateToken(authentication, ACCESS_TOKEN_VALIDITY_SECONDS_REMEMBER_ME);
        else token = jwtTokenUtil.generateToken(authentication, ACCESS_TOKEN_VALIDITY_SECONDS);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @RequestMapping(value = "/check-login", method = RequestMethod.POST)
    public ResponseEntity<?> checkLogin(@RequestBody AuthToken token) {
        String tok = token.getToken();
        if (jwtTokenUtil.isTokenExpired(tok))
            throw new TokenExpirationException("token is expired!");
        String userName = jwtTokenUtil.getUsernameFromToken(tok);
        User user = userService.findByUsername(userName);
        //return ResponseEntity.ok(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/changeUser/{username}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable String username, @RequestHeader HttpHeaders httpHeaders, @RequestBody UserDto user) throws Exception {
        String token = getTokenFromHeader(httpHeaders);
        if (!jwtTokenUtil.getUsernameFromToken(token).equals(username))
            throw new Exception("Bad credentials!");
        User userReal = userService.findByUsername(username);
        userReal.setUsername(user.getUsername());
        userReal.setEmail(user.getEmail());
        return userService.save(userReal);
    }

    @RequestMapping(value = "/changePassword/{username}", method = RequestMethod.PUT)
    public User changePassword(@PathVariable String username, @RequestHeader HttpHeaders httpHeaders, @RequestBody Map<String, String> passwords) throws Exception {
        String token = getTokenFromHeader(httpHeaders);
        if (!jwtTokenUtil.getUsernameFromToken(token).equals(username))
            throw new Exception("Bad credentials!");
        String passwordOld = passwords.get("OldPassword");
        String password = passwords.get("password");
        return  userService.changePassword(username, passwordOld, password);
    }


    private String getTokenFromHeader(HttpHeaders httpHeaders) {
        Map<String, String> headerMap = httpHeaders.toSingleValueMap();
        String token = headerMap.get("authorization");
        token = token.replace("Bearer ", "");
        return token;
    }



}
