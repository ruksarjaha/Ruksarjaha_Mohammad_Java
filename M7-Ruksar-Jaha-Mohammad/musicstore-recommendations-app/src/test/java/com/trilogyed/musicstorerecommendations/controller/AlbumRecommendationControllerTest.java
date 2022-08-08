package com.trilogyed.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogyed.musicstorerecommendations.repository.AlbumRecommendationRepository;
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
@WebMvcTest(AlbumRecommendationController.class)

public class AlbumRecommendationControllerTest {

    @Autowired
    MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    AlbumRecommendationRepository albumRepo;

    private AlbumRecommendation album;


    @Before
    public void setup() throws Exception {
        setUpAlbumRecommendationMock();
    }

    public void setUpAlbumRecommendationMock() {
        album = new AlbumRecommendation();
        album.setAlbumId(1);
        album.setUserId(1);
        album.setLiked(true);

        AlbumRecommendation album1 = new AlbumRecommendation();
        album1.setId(1);
        album1.setAlbumId(1);
        album1.setUserId(1);
        album1.setLiked(true);

        AlbumRecommendation album2 = new AlbumRecommendation();
        album2.setId(2);
        album2.setAlbumId(1);
        album2.setUserId(1);
        album2.setLiked(false);

        List<AlbumRecommendation> albumRecList = new ArrayList<>();
        albumRecList.add(album1);
        albumRecList.add(album2);

        doReturn(albumRecList).when(albumRepo).findAll();
        doReturn(album1).when(albumRepo).save(album);

    }

    @Test
    public void shouldCreateAlbumRecommendationAndReturnStatus201() throws Exception {
        album = new AlbumRecommendation();
        album.setAlbumId(1);
        album.setUserId(1);
        album.setLiked(true);

        AlbumRecommendation album = new AlbumRecommendation();
        album.setId(1);
        album.setAlbumId(1);
        album.setUserId(1);
        album.setLiked(true);

        String outputJson = mapper.writeValueAsString(album);
        String inputJson = mapper.writeValueAsString(album);

        doReturn(album).when(albumRepo).save(album);

        mockMvc.perform(post("/album-recommendation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));


    }

    @Test
    public void shouldReturnAllAlbumRecommendationsAndStatus200() throws Exception {
        AlbumRecommendation albumWithId = new AlbumRecommendation();
        albumWithId.setId(1);
        albumWithId.setAlbumId(1);
        albumWithId.setUserId(1);
        albumWithId.setLiked(true);

        AlbumRecommendation album = new AlbumRecommendation();
        album.setId(2);
        album.setAlbumId(1);
        album.setUserId(1);
        album.setLiked(false);

        List<AlbumRecommendation> albumRecList = new ArrayList<>();
        albumRecList.add(albumWithId);
        albumRecList.add(album);

        doReturn(albumRecList).when(albumRepo).findAll();

        String expectedJsonValue = mapper.writeValueAsString(albumRecList);

        mockMvc.perform(get("/album-recommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJsonValue));

    }


    @Test
    public void shouldUpdateAlbumRecommendationAndReturnStatus204() throws Exception {

        AlbumRecommendation actualAlbum = new AlbumRecommendation();
        actualAlbum.setId(2);
        actualAlbum.setAlbumId(3);
        actualAlbum.setUserId(4);
        actualAlbum.setLiked(true);

        AlbumRecommendation expAlbum = new AlbumRecommendation();
        expAlbum.setId(2);
        expAlbum.setAlbumId(3);
        expAlbum.setUserId(4);
        expAlbum.setLiked(false);

        String inputJson = mapper.writeValueAsString(actualAlbum);
        String outputJson = mapper.writeValueAsString(expAlbum);

        when(albumRepo.save(actualAlbum)).thenReturn(expAlbum);

        mockMvc.perform(
                        put("/album-recommendation/2")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422ErrorIfUpdatingWrongAlbumRecommendation() throws Exception {

        AlbumRecommendation actualAlbum = new AlbumRecommendation();
        actualAlbum.setId(2);
        actualAlbum.setAlbumId(3);
        actualAlbum.setUserId(4);
        actualAlbum.setLiked(true);

        AlbumRecommendation expAlbum = new AlbumRecommendation();
        expAlbum.setId(2);
        expAlbum.setAlbumId(3);
        expAlbum.setUserId(4);
        expAlbum.setLiked(false);

        String inputJson = mapper.writeValueAsString(actualAlbum);
        String outputJson = mapper.writeValueAsString(expAlbum);

        when(albumRepo.save(actualAlbum)).thenReturn(expAlbum);

        mockMvc.perform(
                        put("/album-recommendation/3")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void ShouldFindAlbumRecommendationByIdAndReturnStatus200() throws Exception {

        AlbumRecommendation album = new AlbumRecommendation();
        album.setId(2);
        album.setAlbumId(5);
        album.setUserId(8);
        album.setLiked(false);

        Optional<AlbumRecommendation> found = Optional.of(album);
        when(albumRepo.findById(2)).thenReturn(found);

        String expectedJsonValue = mapper.writeValueAsString(album);

        mockMvc.perform(get("/album-recommendation/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJsonValue));

    }

    @Test
    public void ShouldReturn422ErrorIfAlbumRecommendationNotFound() throws Exception {
        AlbumRecommendation thisAlbum = new AlbumRecommendation();
        thisAlbum.setId(2);
        thisAlbum.setAlbumId(5);
        thisAlbum.setUserId(8);
        thisAlbum.setLiked(false);

        Optional<AlbumRecommendation> found = Optional.of(thisAlbum);
        when(albumRepo.findById(2)).thenReturn(found);

        String expectedJsonValue = mapper.writeValueAsString(thisAlbum);

        mockMvc.perform(get("/album-recommendation/5"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }



    @Test
    public void ShouldDeleteAlbumRecommendationByIdAndReturnStatus204() throws Exception {
        AlbumRecommendation album = new AlbumRecommendation();
        album.setId(12);
        album.setAlbumId(5);
        album.setUserId(8);
        album.setLiked(true);

        Optional<AlbumRecommendation> found = Optional.of(album);
        when(albumRepo.findById(12)).thenReturn(found);

        albumRepo.delete(album);
        Mockito.verify(albumRepo, times(1)).delete(album);

        String expectedJsonValue = mapper.writeValueAsString(album);

        mockMvc.perform(delete("/album-recommendation/12")) //Act
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void ShouldReturn422ErrorOnDeleteIfAlbumRecommendationNotExists() throws Exception {
        AlbumRecommendation album = new AlbumRecommendation();
        album.setId(1);
        album.setAlbumId(5);
        album.setUserId(8);
        album.setLiked(true);

        Optional<AlbumRecommendation> found = Optional.of(album);
        when(albumRepo.findById(1)).thenReturn(found);

        albumRepo.delete(album);
        Mockito.verify(albumRepo, times(1)).delete(album);

        String expectedJsonValue = mapper.writeValueAsString(album);

        mockMvc.perform(delete("/album-recommendation/2")) //Act
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}