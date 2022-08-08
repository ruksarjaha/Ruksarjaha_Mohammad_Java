package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.repository.LabelRepository;
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
@WebMvcTest(LabelController.class)

public class LabelControllerTest {

    @MockBean
    LabelRepository labelRepository;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private Label label;
    private String labelJson;
    private List<Label> allLabels = new ArrayList<>();
    private String allLabelsJson;

    @Before
    public void setup() throws Exception {
        setUpLabelMock();
    }

    public void setUpLabelMock() {
        label = new Label();
        label.setName("BTS");
        label.setWebsite("www.bts.com");

        Label labelWithId = new Label();
        labelWithId.setId(1);
        labelWithId.setName("Maroon5");
        labelWithId.setWebsite("www.maroon5.com");

        Label label1 = new Label();
        label1.setId(2);
        label1.setName("Avicii");
        label1.setWebsite("www.avicii.com");

        allLabels.add(labelWithId);
        allLabels.add(label1);

        doReturn(allLabels).when(labelRepository).findAll();
        doReturn(labelWithId).when(labelRepository).save(label);

    }

    @Test
    public void shouldReturnAllLabels() throws Exception{

        doReturn(allLabels).when(labelRepository).findAll();

        String expectedJsonValue = mapper.writeValueAsString(allLabels);

        mockMvc.perform(get("/label"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJsonValue));
    }


    @Test
    public void ShouldFindLabelByIdAndReturnStatus200() throws Exception {
        Label label2 = new Label();
        label2.setId(1);
        label2.setName("Coldplay");
        label2.setWebsite("www.coldplay.com");

        Optional<Label> thisLabel = Optional.of(label2);
        when(labelRepository.findById(1)).thenReturn(thisLabel);

        if (thisLabel.isPresent()){
            label2 = thisLabel.get();
        }

        String expectedJsonValue = mapper.writeValueAsString(label2);

        mockMvc.perform(get("/label/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJsonValue));

    }

    @Test
    public void shouldReturnAllLabelsAndStatus200() throws Exception {
        List<Label> labelList = new ArrayList<>();

        Label label2 = new Label();
        label2.setId(1);
        label2.setName("Coldplay");
        label2.setWebsite("www.coldplay.com");

        Label label3 = new Label();
        label3.setId(2);
        label3.setName("R-band");
        label3.setWebsite("www.r-band.io");

        labelList.add(label2);
        labelList.add(label3);

        doReturn(labelList).when(labelRepository).findAll();

        String expectedJsonValue = mapper.writeValueAsString(labelList);

        mockMvc.perform(get("/label"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJsonValue));

    }

    @Test
    public void shouldCreateLabelAndReturnNewLabelAndStatus200() throws Exception {

        Label actualLabel = new Label();
        actualLabel.setId(1);
        actualLabel.setName("Coldplay");
        actualLabel.setWebsite("www.coldplay.com");

        Label expectedLabel = new Label();
        expectedLabel.setId(1);
        expectedLabel.setName("Coldplay");
        expectedLabel.setWebsite("www.coldplay.com");

        String outputProduceJson = mapper.writeValueAsString(expectedLabel);
        String inputProduceJson = mapper.writeValueAsString(actualLabel);

        // doReturn(expectedTrack).when(labelRepository).save(actualLabel);
        when(labelRepository.save(actualLabel)).thenReturn(expectedLabel);

        mockMvc.perform(post("/label")
                        .content(inputProduceJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(outputProduceJson));

    }

    @Test
    public void shouldUpdateTrackAndReturnStatus204() throws Exception {

        Label actualLabel = new Label();
        actualLabel.setId(1);
        actualLabel.setName("Coldplay");
        actualLabel.setWebsite("www.coldplay.com");

        Label expectedLabel = new Label();
        actualLabel.setId(1);
        actualLabel.setName("Coldplay");
        actualLabel.setWebsite("www.coldplay.io");

        String outputJson = mapper.writeValueAsString(expectedLabel);
        String inputJson = mapper.writeValueAsString(actualLabel);

        when(labelRepository.save(actualLabel)).thenReturn(expectedLabel);

        mockMvc.perform(
                        put("/label")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void ShouldFindAndDeleteLabelAndReturnStatus204() throws Exception {

        Label label1 = new Label();
        label1.setId(2);
        label1.setName("Avicii");
        label1.setWebsite("www.avicii.com");

        Optional<Label> thisLabel = Optional.of(label1);
        when(labelRepository.findById(2)).thenReturn(thisLabel);

        labelRepository.delete(label1);
        //verify that label repo invokes the method .delete. number of times we tell the method 1 in this case
        Mockito.verify(labelRepository, times(1)).delete(label1);

        String expectedJsonValue = mapper.writeValueAsString(label1);

        mockMvc.perform(delete("/label/2")) //Act
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}