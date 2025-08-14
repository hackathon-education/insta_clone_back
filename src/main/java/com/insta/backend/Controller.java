package com.insta.backend;

import com.insta.backend.Model.Users;
import com.insta.backend.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/")
public class Controller {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody Users user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody Users user) {
        return ResponseEntity.ok(userService.loginUser(user));
    }
}
