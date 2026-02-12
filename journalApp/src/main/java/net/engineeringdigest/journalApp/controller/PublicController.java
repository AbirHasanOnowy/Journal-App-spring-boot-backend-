package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health")
    public static String getHealthStatus() {
        return "Server is up and running";
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createNewUser(@RequestBody User newUser) {
        try {
            User user = userService.saveUser(newUser);
            if(user==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create user. Reason: "+e.getMessage());
        }
    }
}
