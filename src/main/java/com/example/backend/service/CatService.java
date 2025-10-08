package com.example.backend.service;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.Comparator;

import com.example.backend.model.Cat;

@Service
public class CatService {

    private final String jsonFilePath;
    private final Map<String, Cat> catsMap = new ConcurrentHashMap<>();

    public CatService() {
        this("/cats.json");
    }

    public CatService(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
    }

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass().getResourceAsStream(this.jsonFilePath)) {
            JsonNode rootNode = mapper.readTree(is);
            JsonNode imagesNode = rootNode.get("images");
        
            mapper.readValue(
                imagesNode.traverse(),
                new TypeReference<List<Cat>>() {}
            ).forEach(cat -> {
                catsMap.put(cat.getId(), cat);
            });
        } catch (Exception e) {
            throw new RuntimeException("Impossible to load cats from the JSON file", e);
        }
    }

    public Optional<Cat> getById(String id) {
        return Optional.ofNullable(catsMap.get(id));
    }

    public List<Cat> getAll() {
        return List.copyOf(catsMap.values());
    }

    public List<Cat> getAllRanked() {
        List<Cat> cats = new ArrayList<>(catsMap.values());
        cats.sort(Comparator.comparingInt(Cat::getNumberOfVotes).reversed());
        return cats;
    }

    public boolean incrementNumberOfVotes(String id, int increment) {
        return getById(id).map(cat -> {
            cat.setNumberOfVotes(cat.getNumberOfVotes() + increment);
            return true;
        }).orElse(false);
    }
}
