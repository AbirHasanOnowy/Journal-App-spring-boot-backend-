package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @GetMapping
    public List<JournalEntry> getAllEntries() {
        return null;
    }

    @PostMapping
    public JournalEntry createNewEntry(@RequestBody JournalEntry newEntry) {
        return null;
    }

    @GetMapping("/id/{id}")
    public JournalEntry getEntryById(@PathVariable String id) {
        return null;
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateEntry(@RequestBody JournalEntry newEntry, @PathVariable String id) {
        return null;
    }

    @DeleteMapping("/id/{id}")
    public JournalEntry deleteEntryById(@PathVariable String id) {
        return null;
    }
}
