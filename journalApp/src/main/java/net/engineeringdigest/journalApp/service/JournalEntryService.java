package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public List<JournalEntry> getAllEntry() {
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> getEntryById(String id) {
        return journalEntryRepository.findById(id);
    }
    public void deleteEntryById(String id) {
        journalEntryRepository.deleteById(id);
    }
    public JournalEntry saveEntry(JournalEntry newEntry) {
        return journalEntryRepository.save(newEntry);
    }

}
