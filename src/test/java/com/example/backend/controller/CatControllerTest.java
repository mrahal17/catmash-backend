package com.example.backend.controller;

import com.example.backend.service.CatService;
import com.example.backend.model.Cat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;

import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatService catService;

    private Cat cat1;
    private Cat cat2;
    private String nonExistingId = "123abc";

    @BeforeEach
    void setUp() {
        cat1 = new Cat("46h", "http://25.media.tumblr.com/tumblr_m3tpx8fsXX1qhwmnpo1_400.jpg");
        cat2 = new Cat("bd8", "http://25.media.tumblr.com/tumblr_lhe1fu7JpG1qgnva2o1_500.png");
    }

    @Test
    void testGetAll() throws Exception {
        List<Cat> cats = Arrays.asList(cat1, cat2);
        Mockito.when(catService.getAll()).thenReturn(cats);

        mockMvc.perform(get("/api/cats/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("46h"))
                .andExpect(jsonPath("$[1].id").value("bd8"));
    }
    
    @Test
    void testGetByIdExists() throws Exception {
        Mockito.when(catService.getById(cat1.getId())).thenReturn(Optional.of(cat1));

        mockMvc.perform(get("/api/cats/" + cat1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value(cat1.getImageUrl()));
    }

    @Test
    void testGetByIdNoExists() throws Exception {
        Mockito.when(catService.getById(nonExistingId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cats/" + nonExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testIncrementNumberOfVotes() throws Exception {
        Mockito.when(catService.incrementNumberOfVotes(cat1.getId())).thenReturn(true);

        mockMvc.perform(patch(String.format("/api/cats/%s/number-of-votes", cat1.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Number of votes updated."));
    }
}
