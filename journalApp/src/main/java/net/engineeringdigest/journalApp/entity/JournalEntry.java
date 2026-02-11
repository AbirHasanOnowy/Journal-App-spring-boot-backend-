package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Document("Journal")
@Data                     //lombok setting for POJO
@NoArgsConstructor        //lombok by default doesn't add no args constructor for Data Annotation
public class JournalEntry {
    @Id
    private String id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
}
