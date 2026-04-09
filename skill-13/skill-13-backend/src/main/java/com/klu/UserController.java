package com.klu;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @GetMapping("/users")
    public List<String> getUsers() {
        return List.of("Ameen", "John", "Sara");
    }
}