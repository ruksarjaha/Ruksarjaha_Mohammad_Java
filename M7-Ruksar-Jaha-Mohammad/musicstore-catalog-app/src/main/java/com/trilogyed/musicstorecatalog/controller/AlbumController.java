package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AlbumController {

    @Autowired
    AlbumRepository albumRepository;

    @GetMapping("/albums")
    public List<Album> getAlbums() {
        return albumRepository.findAll();
    }

    @GetMapping("/albums/{id}")
    public Album getAlbumById(@PathVariable Integer id) throws Exception {

        Optional<Album> foundAlbum = albumRepository.findById(id);

        if (foundAlbum.isPresent() == false ) {
            throw new ChangeSetPersister.NotFoundException();
        }  return albumRepository.findById(id).get();

    }

    @PostMapping("/albums")
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(@RequestBody Album album) {

        return albumRepository.save(album);
    }

    @PutMapping("/albums")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbum(@RequestBody Album album) {

        albumRepository.save(album);
    }

    @DeleteMapping("/albums/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable Integer id) {
        Optional<Album> albumToDelete = albumRepository.findById(id);
        if(albumToDelete.isPresent() == false ){
            throw new IllegalArgumentException("No album with the id "+id);
        }
        albumRepository.deleteById(id);
        //you cannot delete artist because the artist has albums in the store
    }
}
