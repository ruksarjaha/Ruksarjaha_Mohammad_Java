package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ArtistController {

    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/artist")
    public List<Artist> getArtist() {
        return artistRepository.findAll();
    }

    @GetMapping("/artist/{id}")
    public Artist getArtistById(@PathVariable int id) {
        return artistRepository.findById(id).get();
    }

    @PostMapping("/artist")
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@RequestBody Artist artist) {
        return artistRepository.save(artist);
    }

    @PutMapping("/artist")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@RequestBody Artist artist) {

        artistRepository.save(artist);
    }

    @DeleteMapping("/artist/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Integer id) {
        Optional<Artist> artistToDelete = artistRepository.findById(id);
        if(artistToDelete.isPresent() == false ){
            throw new IllegalArgumentException("No artist with the id "+id);
        }
        artistRepository.deleteById(id);
    }
}

