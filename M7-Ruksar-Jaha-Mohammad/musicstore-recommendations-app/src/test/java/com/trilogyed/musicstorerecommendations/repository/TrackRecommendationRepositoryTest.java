package com.trilogyed.musicstorerecommendations.repository;

import com.trilogyed.musicstorerecommendations.model.TrackRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class TrackRecommendationRepositoryTest {

    @Autowired
    TrackRecommendationRepository trackRepository;

    @Before
    public void setUp() throws Exception {
        trackRepository.deleteAll();
    }

    @Test
    public void addGetDeleteTrack() {

        TrackRecommendation track = new TrackRecommendation();
        track.setTrackId(1);
        track.setUserId(1);
        track.setLiked(true);

        track = trackRepository.save(track);

        Optional<TrackRecommendation> track1 = trackRepository.findById(track.getId());

        assertEquals(track1.get(), track);

        trackRepository.deleteById(track.getId());

        track1 = trackRepository.findById(track.getId());

        assertFalse(track1.isPresent());
    }

    @Test
    public void updateTrack() {

        TrackRecommendation track = new TrackRecommendation();
        track.setTrackId(1);
        track.setUserId(1);
        track.setLiked(true);

        track = trackRepository.save(track);

        track.setTrackId(1);
        track.setUserId(1);
        track.setLiked(true);

        trackRepository.save(track);

        Optional<TrackRecommendation> track1 = trackRepository.findById(track.getId());
        assertEquals(track1.get(), track);
    }

    @Test
    public void getAllArtists() {

        TrackRecommendation track = new TrackRecommendation();
        track.setTrackId(1);
        track.setUserId(1);
        track.setLiked(true);

        track = trackRepository.save(track);

        track = new TrackRecommendation();
        track.setTrackId(2);
        track.setUserId(2);
        track.setLiked(false);

        track = trackRepository.save(track);

        List<TrackRecommendation> aList = trackRepository.findAll();
        assertEquals(aList.size(), 2);

    }


}