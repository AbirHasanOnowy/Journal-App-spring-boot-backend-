package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getAllEntry() {
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> getEntryById(String id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteEntryById(String id, String username) {
        try {
            User user = userService.getUserByUsername(username);
            user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
            journalEntryRepository.deleteById(id);
            userService.saveUser(user);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting entry. Error: "+e.getMessage());
        }
    }

    @Transactional
    public JournalEntry saveEntry(JournalEntry newEntry, String username) {
        try {
            User user = userService.getUserByUsername(username);
            JournalEntry journalEntry = journalEntryRepository.save(newEntry);
            user.getJournalEntries().add(journalEntry);
            userService.saveUser(user);
            return journalEntry;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error saving entry. Error: "+e.getMessage());
        }
    }

    public JournalEntry saveEntry(JournalEntry newEntry) {
            return journalEntryRepository.save(newEntry);
    }
}
