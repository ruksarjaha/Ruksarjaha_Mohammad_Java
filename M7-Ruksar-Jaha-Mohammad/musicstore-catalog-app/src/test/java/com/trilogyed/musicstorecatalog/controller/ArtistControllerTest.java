package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArtistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistRepository artistRepo;

    @Autowired

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldReturnAllArtists() throws Exception{

        List<Artist> expectedArtist = new ArrayList<>(Arrays.asList(new Artist(2,"Artist1","Artist1@Instagram","Artist1Tweets"),
                new Artist (3,"Artist2","Artist2@Instagram","Artist2Tweets")));
        Mockito.when(artistRepo.findAll()).thenReturn(expectedArtist);
        String expectedJson = mapper.writeValueAsString(expectedArtist);

        mockMvc.perform(get("/artist"))//Act
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));

    }

    @Test
    public void shouldReturnArtistById()throws Exception{
        Artist expectedArtist = new Artist (3,"Artist2","Artist2@Instagram","Artist2Tweets");
        Optional <Artist> optionalExpected = Optional.of(expectedArtist);
        Mockito.when(artistRepo.findById(3)).thenReturn(optionalExpected);
        String expectedJson = mapper.writeValueAsString(expectedArtist);

        mockMvc.perform(get("/artist/3")) //Act
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));

    }

    @Test
    public void ShouldDeleteArtistById() throws Exception{


        Artist expectedArtist = new Artist (3,"Artist2","Artist2@Instagram","Artist2Tweets");

        Optional<Artist> optionalArtist = Optional.of(expectedArtist);
        Mockito.when(artistRepo.findById(3)).thenReturn(optionalArtist);

        if (optionalArtist.isPresent()){
            expectedArtist = optionalArtist.get();
        }

        String expectedJsonValue = mapper.writeValueAsString(expectedArtist);


        mockMvc.perform(delete("/artist/3")) //Act
                .andDo(print())
                .andExpect(status().isNoContent());

    }



    @Test
    public void ShouldCreateArtist() throws Exception{

        Artist inputBody = new Artist ("Artist2","Artist2@Instagram","Artist2Tweets");
        Artist outputBody = new Artist (3,"Artist2","Artist2@Instagram","Artist2Tweets");
        String outputJson = mapper.writeValueAsString(outputBody);
        String inputJson = mapper.writeValueAsString(inputBody);

        doReturn(outputBody).when(artistRepo).save(inputBody);

        mockMvc.perform(post("/artist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(outputJson));
    }

    @Test
    public void ShouldReturn422ErrorIfArtistNotFound() throws Exception {
        Artist inputBody = new Artist (3,"Artist2","Artist2@Instagram","Artist2Tweets");

        Optional<Artist> optionalArtist= Optional.of(inputBody);
        when(artistRepo.findById(3)).thenReturn(optionalArtist);

        mockMvc.perform(get("/artist/33"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void shouldUpdateArtist() throws Exception {

        Artist inputBody = new Artist ("Artist2","Artist2@Instagram","Artist2Tweets");
        Artist output = new Artist (1,"Artist2","Artist2@Instagram","Artist2Tweets");
        Optional <Artist> optionalOutput = Optional.of(inputBody);
        Mockito.when(artistRepo.save(inputBody)).thenReturn(optionalOutput.get());
        String expectedJson = mapper.writeValueAsString(optionalOutput.get());
        String inputJson = mapper.writeValueAsString(inputBody);

        when(artistRepo.save(inputBody)).thenReturn(output);

        mockMvc.perform(
                        put("/artist")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

    }





}