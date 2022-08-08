package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LabelController {

    @Autowired
    LabelRepository labelRepository;

    @GetMapping("/label")
    public List<Label> getLabel() {
        return labelRepository.findAll();
    }

    @GetMapping("/label/{id}")
    public Label getLabelById(@PathVariable int id) {

        return labelRepository.findById(id).get();
    }

    @PostMapping("/label")
    @ResponseStatus(HttpStatus.CREATED)
    public Label createLabel(@RequestBody Label label) {
        return labelRepository.save(label);
    }

    @PutMapping("/label")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@RequestBody Label label) {

        labelRepository.save(label);
    }

    @DeleteMapping("/label/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Integer id) {
        Optional<Label> labelToDelete = labelRepository.findById(id);
        if(labelToDelete.isPresent() == false ){
            throw new IllegalArgumentException("No label with the id "+id);
        }
        labelRepository.deleteById(id);
    }
}

