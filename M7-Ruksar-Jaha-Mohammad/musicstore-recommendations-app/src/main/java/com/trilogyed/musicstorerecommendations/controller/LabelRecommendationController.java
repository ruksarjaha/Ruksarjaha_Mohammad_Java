package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
import com.trilogyed.musicstorerecommendations.repository.LabelRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LabelRecommendationController {

    @Autowired
    LabelRecommendationRepository labelRecRepo;

    @GetMapping("/label-recommendation")
    public List<LabelRecommendation> getLabels() {
        return labelRecRepo.findAll();
    }

    @GetMapping("/label-recommendation/{id}")
    public LabelRecommendation getLabelById(@PathVariable Integer id) {
        return labelRecRepo.findById(id).get();
    }

    @PostMapping("/label-recommendation")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation createLabel(@RequestBody LabelRecommendation label) {
        return labelRecRepo.save(label);
    }


    @PutMapping("/label-recommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@PathVariable Integer id, @RequestBody LabelRecommendation label) {

        if (label.getId() == null) {
            label.setId(id);
        } else if (label.getId() != id) {
            throw new IllegalArgumentException("Id does not match");
        }
        labelRecRepo.save(label);
    }

    @PutMapping("/label-recommendation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@RequestBody LabelRecommendation label) {

        labelRecRepo.save(label);
    }

    @DeleteMapping("/label-recommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Integer id) {
        Optional<LabelRecommendation> labelToDelete = labelRecRepo.findById(id);
        if(labelToDelete.isPresent() == false ){
            throw new IllegalArgumentException("No label with the id "+id);
        }
        labelRecRepo.deleteById(id);
    }
}
