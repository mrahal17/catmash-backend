package com.example.backend.service;

import com.example.backend.model.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CatServiceTest {

    private CatService catService;
    private String existingId = "46h";
    private String nonExistingId = "123abc";

    @BeforeEach
    void setUp() {
        catService = new CatService();
        catService.init();
    }

    @Test
    void testGetAllNotEmpty() {
        List<Cat> cats = catService.getAll();
        assertFalse(cats.isEmpty(), "List should not be empty");
    }

    @Test
    void testGetByIdExists() {
        Optional<Cat> cat = catService.getById(existingId);
        assertTrue(cat.isPresent(), String.format("Cat with id %s should exist", existingId));
    }

    @Test
    void testGetByIdNotFound() {
        Optional<Cat> cat = catService.getById(nonExistingId);
        assertTrue(cat.isEmpty(), String.format("Cat with id %s should not exist", nonExistingId));
    }

    @Test
    void testIncrementNumberOfVotesIdExists() {
        Optional<Cat> cat = catService.getById(existingId);
        assertTrue(cat.isPresent());

        int numberOfVotesBefore = cat.get().getNumberOfVotes();
        assertEquals(numberOfVotesBefore, 0);

        boolean updated = catService.incrementNumberOfVotes(existingId);
        int numberOfVotesAfter = cat.get().getNumberOfVotes();

        assertTrue(updated, "The number of votes should have been updated.");
        assertEquals(numberOfVotesAfter, 1, String.format("There should be one vote for cat with id %s", existingId));
    }

    @Test
    void testIncrementNumberOfVotesIdNoExists() {
        boolean updated = catService.incrementNumberOfVotes(nonExistingId);
        assertFalse(updated, "The number of votes should not have been updated because the id does not exist.");
    }
}
