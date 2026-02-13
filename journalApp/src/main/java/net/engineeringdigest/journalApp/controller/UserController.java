package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // Will be Reserved for Admin
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
//    }


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User newUser) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User userInDb = userService.getUserByUsername(username);
            if(userInDb != null) {
                userInDb.setUsername(newUser.getUsername());
                userInDb.setPassword(newUser.getPassword());
                return new ResponseEntity<>(userService.saveUser(userInDb),HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new RuntimeException("Username Already Taken. Error: "+e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userRepository.deleteUserByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
