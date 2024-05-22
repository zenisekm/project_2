package cz.engeto.springproject.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class PersonIdService {
    private List<String> personIds = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init() {
        try {
            personIds = Files.readAllLines(Paths.get("src/main/resources/dataPersonId.txt"));
        } catch (IOException e) {
            throw new RuntimeException("Could not read person IDs file", e);
        }
    }

    public synchronized String getNextPersonId() {
        if (personIds.isEmpty()) {
            throw new RuntimeException("No more person IDs available");
        }
        return personIds.remove(0);
    }
}
