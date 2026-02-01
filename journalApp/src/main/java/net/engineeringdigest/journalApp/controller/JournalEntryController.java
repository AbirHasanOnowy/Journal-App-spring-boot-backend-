package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAllEntries() {
        return journalEntryService.getAllEntry();
    }

    @PostMapping
    public JournalEntry createNewEntry(@RequestBody JournalEntry newEntry) {
        newEntry.setDate(LocalDateTime.now());
        return journalEntryService.saveEntry(newEntry);
    }

    @GetMapping("/id/{id}")
    public JournalEntry getEntryById(@PathVariable String id) {
        return journalEntryService.getEntryById(id).orElse(null);
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateEntry(@RequestBody JournalEntry newEntry, @PathVariable String id) {
        JournalEntry oldEntry = journalEntryService.getEntryById(id).orElse(null);
        if(oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            return journalEntryService.saveEntry(oldEntry);
        }
        else {
            newEntry.setDate(LocalDateTime.now());
            return journalEntryService.saveEntry(newEntry);
        }
    }

    @DeleteMapping("/id/{id}")
    public boolean deleteEntryById(@PathVariable String id) {
        journalEntryService.deleteEntryById(id);
        return true;
    }
}
