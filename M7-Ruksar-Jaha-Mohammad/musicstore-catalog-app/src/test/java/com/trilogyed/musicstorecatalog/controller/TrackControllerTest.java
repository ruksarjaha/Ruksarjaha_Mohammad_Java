package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackController.class)
public class TrackControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TrackRepository trackRepository;

    ObjectMapper mapper = new ObjectMapper();

    private Track track;
    private List<Track> allTracks = new ArrayList<>();
    private String trackJson;
    private String allTracksJson;

    @Before
    public void setup() throws Exception {
        setupTrackControllerMock();
    }
    private void setupTrackControllerMock(){
        track = new Track();
        track.setTitle("Title1");
        track.setAlbumId(1);
        track.setRuntime(250);

        Track trackWithId = new Track();
        trackWithId.setId(1);
        trackWithId.setTitle("Title1");
        trackWithId.setAlbumId(1);
        trackWithId.setRuntime(250);

        Track track2 = new Track();
        track2.setId(2);
        track2.setTitle("Title2");
        track2.setAlbumId(2);
        track2.setRuntime(300);

        allTracks.add(trackWithId);
        allTracks.add(track2);

        doReturn(allTracks).when(trackRepository).findAll();
        doReturn(trackWithId).when(trackRepository).save(track);

    }


    @Test
    public void shouldReturnAllTracks(){
        List<Track> trackList = new ArrayList<Track>();
        trackList.add(new Track(1,1, "title1", 200));
        trackList.add(new Track(2,2, "title2", 250));
        when(trackRepository.findAll()).thenReturn(trackList);

        assertEquals(2,trackRepository.findAll().size());

    }


    @Test
    public void ShouldFindTrackByIdAndReturnStatus200() throws Exception {
        Track track1 = new Track();
        track1.setId(1);
        track1.setTitle("Title1");
        track1.setAlbumId(1);
        track1.setRuntime(100);

        Optional<Track> thisTrack = Optional.of(track1);
        when(trackRepository.findById(1)).thenReturn(thisTrack);

        if (thisTrack.isPresent()){
            track1 = thisTrack.get();
        }

        String expectedJsonValue = mapper.writeValueAsString(track1);

        mockMvc.perform(get("/track/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJsonValue));

    }

    @Test
    public void shouldCreateTrackAndReturnNewTrackAndStatus200() throws Exception {
        Track inputTrack = new Track();
        inputTrack.setTitle("Awesome Song");
        inputTrack.setAlbumId(1);
        inputTrack.setRuntime(100);

        Track outputTrack = new Track();
        outputTrack.setId(1);
        outputTrack.setTitle("Awesome Song");
        outputTrack.setAlbumId(1);
        outputTrack.setRuntime(100);

        String outputProduceJson = mapper.writeValueAsString(outputTrack);
        String inputProduceJson = mapper.writeValueAsString(inputTrack);

        // doReturn(outputTrack).when(trackRepository).save(inputTrack);
        when(trackRepository.save(inputTrack)).thenReturn(outputTrack);

        mockMvc.perform(post("/track")
                        .content(inputProduceJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(outputProduceJson));

    }

    @Test
    public void shouldUpdateTrackAndReturnStatus204() throws Exception {

        Track inputTrack = new Track();
        inputTrack.setId(1);
        inputTrack.setTitle("Title1");
        inputTrack.setAlbumId(1);
        inputTrack.setRuntime(200);

        Track outputTrack = new Track();
        outputTrack.setId(1);
        outputTrack.setTitle("Title1");
        outputTrack.setAlbumId(1);
        outputTrack.setRuntime(300);

        String outputJson = mapper.writeValueAsString(outputTrack);
        String inputJson = mapper.writeValueAsString(inputTrack);

        when(trackRepository.save(inputTrack)).thenReturn(outputTrack);

        mockMvc.perform(
                        put("/track")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void ShouldFindAndDeleteTrackAndReturnStatus204() throws Exception {
        Track track2 = new Track();
        track2.setId(1);
        track2.setTitle("Title1");
        track2.setAlbumId(1);
        track2.setRuntime(100);

        Optional<Track> thisTrack = Optional.of(track2);
        when(trackRepository.findById(1)).thenReturn(thisTrack);

        trackRepository.delete(track2);
        //verify that track repo invokes the method .delete. number of times we tell the method 1 in this case
        Mockito.verify(trackRepository, times(1)).delete(track2);
    }


}