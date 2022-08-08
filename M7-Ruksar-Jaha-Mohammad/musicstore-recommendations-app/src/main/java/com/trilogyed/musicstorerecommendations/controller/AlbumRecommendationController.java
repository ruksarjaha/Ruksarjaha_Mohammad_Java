package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogyed.musicstorerecommendations.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AlbumRecommendationController {

    @Autowired
    AlbumRecommendationRepository albumRecRepo;

    @GetMapping("/album-recommendation")
    public List<AlbumRecommendation> getAlbums() {
        return albumRecRepo.findAll();
    }

    @GetMapping("/album-recommendation/{id}")
    public AlbumRecommendation getAlbumById(@PathVariable Integer id) {
        return albumRecRepo.findById(id).get();
    }

    @PostMapping("/album-recommendation")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation createAlbum(@RequestBody AlbumRecommendation album) {
        return albumRecRepo.save(album);
    }

    @PutMapping("/album-recommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbum(@PathVariable Integer id, @RequestBody AlbumRecommendation album) {

        if (album.getId() == null) {
            album.setId(id);
        } else if (album.getId() != id) {
            throw new IllegalArgumentException("Id does not match");
        }
        albumRecRepo.save(album);
    }

    @PutMapping("/album-recommendation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbum(@RequestBody AlbumRecommendation album) {

        albumRecRepo.save(album);
    }

    @DeleteMapping("/album-recommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable Integer id) {
        Optional<AlbumRecommendation> albumToDelete = albumRecRepo.findById(id);
        if (albumToDelete.isPresent() == false) {
            throw new IllegalArgumentException("No album with the id " + id);
        }
        albumRecRepo.deleteById(id);
    }
}
