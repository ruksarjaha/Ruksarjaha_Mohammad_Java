package com.trilogyed.musicstorecatalog.repository;

import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.model.Track;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TrackRepositoryTest {

    @Autowired
    TrackRepository trackRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    LabelRepository labelRepository;

    @Before
    public void setUp() throws Exception {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        labelRepository.deleteAll();
    }

    @Test
    public void addGetDeleteTrack() {

        // Need to create a Label, Artist, and Album first
        Label label = new Label();
        label.setName("A&M");
        label.setWebsite("www.aandm.com");
        label = labelRepository.save(label);

        Artist artist = new Artist();
        artist.setName("Rock Start");
        artist.setInstagram("@RockStart");
        artist.setTwitter("@TheRockStar");
        artist = artistRepository.save(artist);

        Album album = new Album();
        album.setTitle("Greatest Hits");
        album.setArtistId(artist.getId());
        album.setLabelId(label.getId());
        album.setReleaseDate(LocalDate.of(2010, 1, 5));
        album.setListPrice(new BigDecimal("21.95"));

        album = albumRepository.save(album);

        Track track = new Track();
        track.setTitle("The Big Hit");
        track.setRuntime(180);
        track.setAlbumId(album.getId());
        track = trackRepository.save(track);

        Optional<Track> track1 = trackRepository.findById(track.getId());

        assertEquals(track1.get(), track);

        trackRepository.deleteById(track.getId());

        track1 = trackRepository.findById(track.getId());

        assertFalse(track1.isPresent());
    }

    @Test
    public void shouldUpdateTrack() {

        // Need to create a Label, Artist, and Album first
        Label label = new Label();
        label.setName("A&M");
        label.setWebsite("www.aandm.com");
        label = labelRepository.save(label);

        Artist artist = new Artist();
        artist.setName("Rock Start");
        artist.setInstagram("@RockStart");
        artist.setTwitter("@TheRockStar");
        artist = artistRepository.save(artist);

        Album album = new Album();
        album.setTitle("Greatest Hits");
        album.setArtistId(artist.getId());
        album.setLabelId(label.getId());
        album.setReleaseDate(LocalDate.of(2010, 1, 5));
        album.setListPrice(new BigDecimal("21.95"));

        album = albumRepository.save(album);

        Track track = new Track();
        track.setTitle("The Big Hit");
        track.setRuntime(180);
        track.setAlbumId(album.getId());
        track = trackRepository.save(track);

        track.setTitle("NEW TITLE");
        track.setRuntime(12);

        trackRepository.save(track);

        Optional<Track> track1 = trackRepository.findById(track.getId());

        assertEquals(track1.get(), track);

    }

    @Test
    public void getAllTracks() {

        // Need to create a Label, Artist, and Album first
        Label label = new Label();
        label.setName("A&M");
        label.setWebsite("www.aandm.com");
        label = labelRepository.save(label);

        Artist artist = new Artist();
        artist.setName("Rock Start");
        artist.setInstagram("@RockStart");
        artist.setTwitter("@TheRockStar");
        artist = artistRepository.save(artist);

        Album album = new Album();
        album.setTitle("Greatest Hits");
        album.setArtistId(artist.getId());
        album.setLabelId(label.getId());
        album.setReleaseDate(LocalDate.of(2010, 1, 5));
        album.setListPrice(new BigDecimal("21.95"));

        album = albumRepository.save(album);

        Album album1 = new Album();
        album1.setTitle("Lesser Hits");
        album1.setArtistId(artist.getId());
        album1.setLabelId(label.getId());
        album1.setReleaseDate(LocalDate.of(2012, 6, 25));
        album1.setListPrice(new BigDecimal("9.95"));

        album1 = albumRepository.save(album1);


        Track track = new Track();
        track.setTitle("The Big Hit");
        track.setRuntime(180);
        track.setAlbumId(album.getId());
        track = trackRepository.save(track);

        track = new Track();
        track.setTitle("Just A Song");
        track.setRuntime(120);
        track.setAlbumId(album1.getId());
        track = trackRepository.save(track);

        List<Track> tList = trackRepository.findAll();

        assertEquals(tList.size(), 2);

    }

}
