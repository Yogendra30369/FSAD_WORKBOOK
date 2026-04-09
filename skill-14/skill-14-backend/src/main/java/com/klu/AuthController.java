package com.klu;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    private Map<String, User> users = new HashMap<>();

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        users.put(user.getUsername(), user);
        return "Registered Successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User u = users.get(user.getUsername());
        if (u != null && u.getPassword().equals(user.getPassword())) {
            return "Login Success";
        }
        return "Invalid Credentials";
    }

    @GetMapping("/profile/{username}")
    public User getProfile(@PathVariable String username) {
        return users.get(username);
    }
}