package com.example.backend.controller;

import com.example.backend.model.Cat;
import com.example.backend.service.CatService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/cats")
public class CatController {

    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cat> getById(@PathVariable String id) {
        return catService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public List<Cat> getAll() {
        return catService.getAll();
    }

    @GetMapping("/ranked")
    public List<Cat> getAllRanked() {
        return catService.getAllRanked();
    }

    @PatchMapping("/{id}/number-of-votes")
    public ResponseEntity<String> incrementNumberOfVotes(
        @PathVariable String id,
        @RequestBody Map<String, Object> payload) {
        int increment = 1;

        if (payload != null && payload.containsKey("increment")) {
            Object incObj = payload.get("increment");
            if (incObj instanceof Number) increment = ((Number) incObj).intValue();
        }

        boolean updated = catService.incrementNumberOfVotes(id, increment);

        if (updated) {
            return ResponseEntity.ok("Number of votes updated by " + increment + ".");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}