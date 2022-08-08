package com.trilogyed.musicstorerecommendations.repository;

import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
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

public class LabelRecommendationRepositoryTest {

    @Autowired
    LabelRecommendationRepository labelRepository;

    @Before
    public void setUp() throws Exception {
        labelRepository.deleteAll();
    }

    @Test
    public void addGetDeleteLabel() {

        LabelRecommendation label = new LabelRecommendation();
        label.setLabelId(1);
        label.setUserId(1);
        label.setLiked(true);

        label = labelRepository.save(label);

        Optional<LabelRecommendation> label1 = labelRepository.findById(label.getId());

        assertEquals(label1.get(), label);

        labelRepository.deleteById(label.getId());

        label1 = labelRepository.findById(label.getId());

        assertFalse(label1.isPresent());
    }

    @Test
    public void updateLabel() {

        LabelRecommendation label = new LabelRecommendation();
        label.setLabelId(1);
        label.setUserId(1);
        label.setLiked(true);

        label = labelRepository.save(label);

        label.setLabelId(1);
        label.setUserId(1);
        label.setLiked(false);

        labelRepository.save(label);

        Optional<LabelRecommendation> label1 = labelRepository.findById(label.getId());
        assertEquals(label1.get(), label);
    }

    @Test
    public void getAllLabels() {

        LabelRecommendation label = new LabelRecommendation();
        label.setLabelId(1);
        label.setUserId(1);
        label.setLiked(true);

        label = labelRepository.save(label);

        label = new LabelRecommendation();
        label.setLabelId(2);
        label.setUserId(2);
        label.setLiked(false);

        label = labelRepository.save(label);

        List<LabelRecommendation> lList = labelRepository.findAll();
        assertEquals(lList.size(), 2);

    }


}