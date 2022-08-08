package com.trilogyed.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.repository.ArtistRecommendationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistRecommendationController.class)

public class ArtistRecommendationControllerTest {

    @Autowired
    MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    ArtistRecommendationRepository artistRepo;

    private ArtistRecommendation artist;


    @Before
    public void setup() throws Exception {
        setUpArtistRecommendationMock();
    }

    public void setUpArtistRecommendationMock() {
        artist = new ArtistRecommendation();
        artist.setArtistId(1);
        artist.setUserId(1);
        artist.setLiked(true);

        ArtistRecommendation artistWithId = new ArtistRecommendation();
        artistWithId.setId(1);
        artistWithId.setArtistId(1);
        artistWithId.setUserId(1);
        artistWithId.setLiked(true);

        ArtistRecommendation otherArtist = new ArtistRecommendation();
        otherArtist.setId(2);
        otherArtist.setArtistId(1);
        otherArtist.setUserId(1);
        otherArtist.setLiked(false);

        List<ArtistRecommendation> artistRecList = new ArrayList<>();
        artistRecList.add(artistWithId);
        artistRecList.add(otherArtist);

        doReturn(artistRecList).when(artistRepo).findAll();
        doReturn(artistWithId).when(artistRepo).save(artist);

    }

    @Test
    public void shouldCreateArtistRecommendationAndReturnStatus201() throws Exception {
        artist = new ArtistRecommendation();
        artist.setArtistId(1);
        artist.setUserId(1);
        artist.setLiked(true);

        ArtistRecommendation artistWithId = new ArtistRecommendation();
        artistWithId.setId(1);
        artistWithId.setArtistId(1);
        artistWithId.setUserId(1);
        artistWithId.setLiked(true);

        String outputJson = mapper.writeValueAsString(artistWithId);
        String inputJson = mapper.writeValueAsString(artist);

        doReturn(artistWithId).when(artistRepo).save(artist);

        mockMvc.perform(post("/artist-recommendation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));


    }

    @Test
    public void shouldReturnAllArtistRecommendationsAndStatus200() throws Exception {
        ArtistRecommendation artistWithId = new ArtistRecommendation();
        artistWithId.setId(1);
        artistWithId.setArtistId(1);
        artistWithId.setUserId(1);
        artistWithId.setLiked(true);

        ArtistRecommendation otherArtist = new ArtistRecommendation();
        otherArtist.setId(2);
        otherArtist.setArtistId(1);
        otherArtist.setUserId(1);
        otherArtist.setLiked(false);

        List<ArtistRecommendation> artistRecList = new ArrayList<>();
        artistRecList.add(artistWithId);
        artistRecList.add(otherArtist);

        doReturn(artistRecList).when(artistRepo).findAll();

        String expectedJsonValue = mapper.writeValueAsString(artistRecList);

        mockMvc.perform(get("/artist-recommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJsonValue));

    }

    @Test
    public void ShouldFindArtistRecommendationByIdAndReturnStatus200() throws Exception {

        ArtistRecommendation thisArtist = new ArtistRecommendation();
        thisArtist.setId(2);
        thisArtist.setArtistId(5);
        thisArtist.setUserId(8);
        thisArtist.setLiked(false);

        Optional<ArtistRecommendation> found = Optional.of(thisArtist);
        when(artistRepo.findById(2)).thenReturn(found);

        String expectedJsonValue = mapper.writeValueAsString(thisArtist);

        mockMvc.perform(get("/artist-recommendation/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJsonValue));

    }

    @Test
    public void ShouldReturn422ErrorIfArtistRecommendationNotFound() throws Exception {
        ArtistRecommendation thisArtist = new ArtistRecommendation();
        thisArtist.setId(2);
        thisArtist.setArtistId(5);
        thisArtist.setUserId(8);
        thisArtist.setLiked(false);

        Optional<ArtistRecommendation> found = Optional.of(thisArtist);
        when(artistRepo.findById(2)).thenReturn(found);

        String expectedJsonValue = mapper.writeValueAsString(thisArtist);

        mockMvc.perform(get("/artist-recommendation/5"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void ShouldDeleteArtistRecommendationByIdAndReturnStatus204() throws Exception {
        ArtistRecommendation badRecommendation = new ArtistRecommendation();
        badRecommendation.setId(12);
        badRecommendation.setArtistId(5);
        badRecommendation.setUserId(8);
        badRecommendation.setLiked(true);

        Optional<ArtistRecommendation> found = Optional.of(badRecommendation);
        when(artistRepo.findById(12)).thenReturn(found);

        artistRepo.delete(badRecommendation);
        Mockito.verify(artistRepo, times(1)).delete(badRecommendation);

        String expectedJsonValue = mapper.writeValueAsString(badRecommendation);

        mockMvc.perform(delete("/artist-recommendation/12")) //Act
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void ShouldReturn422ErrorOnDeleteIfArtistRecommendationNotExists() throws Exception {
        ArtistRecommendation badRecommendation = new ArtistRecommendation();
        badRecommendation.setId(12);
        badRecommendation.setArtistId(5);
        badRecommendation.setUserId(8);
        badRecommendation.setLiked(true);

        Optional<ArtistRecommendation> found = Optional.of(badRecommendation);
        when(artistRepo.findById(12)).thenReturn(found);

        artistRepo.delete(badRecommendation);
        Mockito.verify(artistRepo, times(1)).delete(badRecommendation);

        String expectedJsonValue = mapper.writeValueAsString(badRecommendation);

        mockMvc.perform(delete("/artist-recommendation/1")) //Act
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}