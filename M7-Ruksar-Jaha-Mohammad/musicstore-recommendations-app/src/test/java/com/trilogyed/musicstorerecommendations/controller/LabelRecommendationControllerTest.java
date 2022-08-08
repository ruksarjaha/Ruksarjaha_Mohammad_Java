package com.trilogyed.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
import com.trilogyed.musicstorerecommendations.repository.LabelRecommendationRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LabelRecommendationController.class)

public class LabelRecommendationControllerTest {

    @MockBean
    LabelRecommendationRepository labelRepo;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private LabelRecommendation label;
    private String labelJson;
    private List<LabelRecommendation> allLabels = new ArrayList<>();
    private String allLabelsJson;

    @Before
    public void setup() throws Exception {
        setUpLabelRecommendationMock();
    }

    public void setUpLabelRecommendationMock() {
        label = new LabelRecommendation();
        label.setId(1);
        label.setLabelId(1);
        label.setUserId(1);
        label.setLiked(true);

        LabelRecommendation labelWithId = new LabelRecommendation();
        labelWithId.setId(2);
        labelWithId.setLabelId(2);
        labelWithId.setUserId(2);
        label.setLiked(false);

        LabelRecommendation label1 = new LabelRecommendation();
        label1.setId(3);
        label1.setLabelId(3);
        label1.setUserId(3);
        label1.setLiked(true);

        allLabels.add(labelWithId);
        allLabels.add(label1);

        doReturn(allLabels).when(labelRepo).findAll();
        doReturn(labelWithId).when(labelRepo).save(label);

    }

    @Test
    public void shouldReturnAllLabelRecommendations() throws Exception{

        doReturn(allLabels).when(labelRepo).findAll();

        String expectedJsonValue = mapper.writeValueAsString(allLabels);

        mockMvc.perform(get("/label-recommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJsonValue));
    }


    @Test
    public void ShouldFindLabelRecommendationsByIdAndReturnStatus200() throws Exception {
        LabelRecommendation label = new LabelRecommendation();
        label.setId(1);
        label.setLabelId(1);
        label.setUserId(1);
        label.setLiked(true);

        Optional<LabelRecommendation> thisLabel = Optional.of(label);
        when(labelRepo.findById(1)).thenReturn(thisLabel);

        if (thisLabel.isPresent()){
            label = thisLabel.get();
        }

        String expectedJsonValue = mapper.writeValueAsString(label);

        mockMvc.perform(get("/label-recommendation/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJsonValue));

    }

    @Test
    public void shouldReturnAllLabelRecommendationsAndStatus200() throws Exception {
        List<LabelRecommendation> labelList = new ArrayList<>();

        LabelRecommendation label = new LabelRecommendation();
        label.setId(1);
        label.setLabelId(1);
        label.setUserId(1);
        label.setLiked(true);

        LabelRecommendation label1 = new LabelRecommendation();
        label1.setId(2);
        label1.setLabelId(2);
        label1.setUserId(2);
        label1.setLiked(false);

        labelList.add(label);
        labelList.add(label1);

        doReturn(labelList).when(labelRepo).findAll();

        String expectedJsonValue = mapper.writeValueAsString(labelList);

        mockMvc.perform(get("/label-recommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJsonValue));

    }

    @Test
    public void shouldCreateLabelRecommendationAndReturnNewLabelRecommendationAndStatus200() throws Exception {

        LabelRecommendation actualLabel = new LabelRecommendation();
        actualLabel.setId(1);
        actualLabel.setLabelId(1);
        actualLabel.setUserId(1);
        actualLabel.setLiked(true);

        LabelRecommendation expLabel = new LabelRecommendation();
        expLabel.setId(1);
        expLabel.setLabelId(1);
        expLabel.setUserId(1);
        expLabel.setLiked(true);

        String outputLabelJson = mapper.writeValueAsString(expLabel);
        String inputLabelJson = mapper.writeValueAsString(actualLabel);

        // doReturn(expectedTrack).when(labelRepository).save(actualLabel);
        when(labelRepo.save(actualLabel)).thenReturn(expLabel);

        mockMvc.perform(post("/label-recommendation")
                        .content(inputLabelJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(outputLabelJson));

    }

    @Test
    public void shouldUpdateLabelRecommendationAndReturnStatus204() throws Exception {

        LabelRecommendation actualLabel = new LabelRecommendation();
        actualLabel.setId(1);
        actualLabel.setLabelId(1);
        actualLabel.setUserId(1);
        actualLabel.setLiked(true);


        LabelRecommendation expLabel = new LabelRecommendation();
        expLabel.setId(1);
        expLabel.setLabelId(2);
        expLabel.setUserId(1);
        actualLabel.setLiked(false);


        String outputJson = mapper.writeValueAsString(expLabel);
        String inputJson = mapper.writeValueAsString(actualLabel);

        when(labelRepo.save(actualLabel)).thenReturn(expLabel);

        mockMvc.perform(
                        put("/label-recommendation/1")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void ShouldFindAndDeleteLabelRecommendationAndReturnStatus204() throws Exception {

        LabelRecommendation label = new LabelRecommendation();
        label.setId(1);
        label.setLabelId(1);
        label.setUserId(1);
        label.setLiked(true);

        Optional<LabelRecommendation> thisLabel = Optional.of(label);
        when(labelRepo.findById(1)).thenReturn(thisLabel);

        labelRepo.delete(label);
        //verify that label repo invokes the method .delete. number of times we tell the method 1 in this case
        Mockito.verify(labelRepo, times(1)).delete(label);

        String expectedJsonValue = mapper.writeValueAsString(label);

        mockMvc.perform(delete("/label-recommendation/1")) //Act
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}