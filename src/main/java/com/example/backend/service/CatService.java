package com.example.backend.service;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.example.backend.model.Cat;

@Service
public class CatService {

    private final Map<String, Cat> cats = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        try (InputStream is = getClass().getResourceAsStream("/cats.json")) {
            List<Cat> list = mapper.readValue(is, new TypeReference<List<Cat>>() {});
            list.forEach(cat -> {
                cats.put(cat.getId(), cat);
            });
        } catch (Exception e) {
            throw new RuntimeException("Impossible to load cats from the JSON file", e);
        }
    }

    public Optional<Cat> getById(String id) {
        return Optional.ofNullable(cats.get(id));
    }

    public List<Cat> getAll() {
        return List.copyOf(cats.values());
    }

    public void updateNumberOfVotes(String id, int newNumberOfVotes) {
        Optional<Cat> cat = Optional.ofNullable(cats.get(id));
        if (cat.isPresent()) {
            cat.get().setNumberOfVotes(newNumberOfVotes);
        }
    }
}
