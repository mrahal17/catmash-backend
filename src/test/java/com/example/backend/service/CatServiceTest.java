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
        Optional<Cat> cat = catService.getById("46h");
        assertTrue(cat.isPresent(), "Cat with id '46h' should exist");
        assertEquals("http://25.media.tumblr.com/tumblr_m3tpx8fsXX1qhwmnpo1_400.jpg", cat.get().getImageUrl());
    }

    @Test
    void testGetByIdNotFound() {
        Optional<Cat> cat = catService.getById("123abc");
        assertTrue(cat.isEmpty(), "Cat with id '123abc' should not exist");
    }

    @Test
    void testIncrementNumberOfVotes() {
        String catId = "46h";
        Optional<Cat> cat = catService.getById(catId);
        assertTrue(cat.isPresent());

        int numberOfVotesBefore = cat.get().getNumberOfVotes();
        assertEquals(numberOfVotesBefore, 0);

        catService.incrementNumberOfVotes(catId);
        int numberOfVotesAfter = cat.get().getNumberOfVotes();

        assertEquals(numberOfVotesAfter, 1, "There should be 1 votes for cat with id '46h'");
    }
}
