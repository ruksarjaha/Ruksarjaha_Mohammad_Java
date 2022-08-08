package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ArtistRecommendationController {

    @Autowired
    ArtistRecommendationRepository artistRecRepo;

    @GetMapping("/artist-recommendation")
    public List<ArtistRecommendation> getArtists() {
        return artistRecRepo.findAll();
    }

    @GetMapping("/artist-recommendation/{id}")
    public ArtistRecommendation getArtistById(@PathVariable Integer id) {
        return artistRecRepo.findById(id).get();
    }

    @PostMapping("/artist-recommendation")
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation createArtist(@RequestBody ArtistRecommendation artist) {
        return artistRecRepo.save(artist);
    }

    @PutMapping("/artist-recommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@PathVariable Integer id, @RequestBody ArtistRecommendation artist) {

        if (artist.getId() == null) {
            artist.setId(id);
        } else if (artist.getId() != id) {
            throw new IllegalArgumentException("Id does not match");
        }
        artistRecRepo.save(artist);
    }

    @PutMapping("/artist-recommendation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@RequestBody ArtistRecommendation artist) {

        artistRecRepo.save(artist);
    }

    @DeleteMapping("/artist-recommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Integer id) {
        Optional<ArtistRecommendation> artistToDelete = artistRecRepo.findById(id);
        if(artistToDelete.isPresent() == false ){
            throw new IllegalArgumentException("No artist recommendations with the id "+id);
        }
        artistRecRepo.deleteById(id);
    }
}
