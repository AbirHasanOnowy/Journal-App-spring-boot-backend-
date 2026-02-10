package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createNewUser(@RequestBody User newUser) {
        try {
            return new ResponseEntity<>(userService.saveUser(newUser),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User newUser) {
        try {
            User userInDb = userService.getUserByUsername(newUser.getUsername());
            if(userInDb != null) {
                userInDb.setUsername(newUser.getUsername());
                userInDb.setPassword(newUser.getPassword());
                return new ResponseEntity<>(userService.saveUser(userInDb),HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new RuntimeException("No User found with username: "+newUser.getUsername());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
