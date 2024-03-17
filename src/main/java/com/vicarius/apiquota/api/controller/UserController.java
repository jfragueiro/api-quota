package com.vicarius.apiquota.api.controller;

import com.vicarius.apiquota.api.service.impl.user.UserService;
import com.vicarius.apiquota.exception.UserNotFoundException;
import com.vicarius.apiquota.model.VicariusUser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/users")
public class UserController {
    private final Map<String, VicariusUser> users = new HashMap<>();


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public VicariusUser createUser(@RequestBody VicariusUser user) {
        userService.createUser(user);
        return user;
    }

    @GetMapping("/{userId}")
    public VicariusUser getUser(@PathVariable String userId) {
        Optional<VicariusUser> userOptional = userService.getUserById(userId);

        return userOptional.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }
    
    @GetMapping()
    public List<VicariusUser> getUsers() {

        return userService.getAllUsers();
    }

    @PutMapping("/{userId}")
    public VicariusUser updateUser(@PathVariable String userId, @RequestBody VicariusUser updatedUser) {

        return userService.updateUser(userId, updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted with ID: " + userId);
    }
}
