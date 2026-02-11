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
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            if(user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(user.getJournalEntries(), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createNewEntry(@RequestBody JournalEntry newEntry, @PathVariable String username) {
        try {
            newEntry.setDate(LocalDateTime.now());
            return new ResponseEntity<>(journalEntryService.saveEntry(newEntry, username), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable String id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(id);
        if(journalEntry.isPresent()) return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{username}/{id}")
    public ResponseEntity<JournalEntry> updateEntry(@RequestBody JournalEntry newEntry, @PathVariable String id) {
        JournalEntry oldEntry = journalEntryService.getEntryById(id).orElse(null);
        if(oldEntry != null) {
            oldEntry.setTitle(!newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            return new ResponseEntity<>(journalEntryService.saveEntry(oldEntry),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable String username,@PathVariable String id) {
        journalEntryService.deleteEntryById(id,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
