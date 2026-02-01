package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
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

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntries() {
        return new ResponseEntity<>(journalEntryService.getAllEntry(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createNewEntry(@RequestBody JournalEntry newEntry) {
        try {
            newEntry.setDate(LocalDateTime.now());
            return new ResponseEntity<>(journalEntryService.saveEntry(newEntry), HttpStatus.CREATED);
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

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateEntry(@RequestBody JournalEntry newEntry, @PathVariable String id) {
        JournalEntry oldEntry = journalEntryService.getEntryById(id).orElse(null);
        if(oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            return new ResponseEntity<>(journalEntryService.saveEntry(oldEntry),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable String id) {
        journalEntryService.deleteEntryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
