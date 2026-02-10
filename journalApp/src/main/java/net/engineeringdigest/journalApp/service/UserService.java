package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
    public User saveUser(User newUser) {
        return userRepository.save(newUser);
    }
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
