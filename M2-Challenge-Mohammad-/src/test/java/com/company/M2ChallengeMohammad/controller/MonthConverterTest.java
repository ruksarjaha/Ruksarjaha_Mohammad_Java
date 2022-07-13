package com.company.M2ChallengeMohammad.controller;

import com.company.M2ChallengeMohammad.Model.Month;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringRunner.class)
@WebMvcTest(MonthConverter.class)

public class MonthConverterTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldReturn404StatusCodeIfMonthNumberNotFound() throws Exception {
        mockMvc.perform(get("/month/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnMonthByMonthNumber() throws Exception {

        // ARRANGE
        Month outputMonth = new Month();
        outputMonth.setName("August");
        outputMonth.setNumber(8);

        String outputJson = mapper.writeValueAsString(outputMonth);

        // ACT
        mockMvc.perform(get("/month/8"))
                .andDo(print())
                .andExpect(status().isOk())                     // ASSERT that we got back 200 OK.
                .andExpect(content().json(outputJson));         // ASSERT that what we're expecting is what we got back.
    }

    @Test
    public void shouldReturn422StatusCodeIfInputOutOfRange() throws Exception {
        mockMvc.perform(get("/month/eight"))  //number has to be integer
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());             // ASSERT (status code is 422)
    }

    @Test
    public void shouldReturnNonEmptyValue() throws Exception {
        mockMvc.perform(get("/randomMonth"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty()  //$- should get something in string(place holder)
                );
    }

    @Test
    public void shouldReturnAValueFromMonthList() throws Exception {
        mockMvc.perform(get("/randomMonth"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.number").isNotEmpty());
    }

}