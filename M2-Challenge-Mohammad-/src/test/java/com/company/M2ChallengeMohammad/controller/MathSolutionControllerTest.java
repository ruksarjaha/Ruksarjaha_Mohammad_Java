package com.company.M2ChallengeMohammad.controller;

import com.company.M2ChallengeMohammad.Model.MathSolution;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MathSolutionController.class)
public class MathSolutionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();


    @Test
    public void shouldBeAddingTwoOperands() throws Exception {
        // ARRANGE
        MathSolution solution1 = new MathSolution(82, 18);
        solution1.setOperations("add");
        solution1.add();

        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(solution1);

        // ACT
        mockMvc.perform(
                        post("/add")                                    // Perform the POST request.
                                .content(inputJson)                               // Set the request body.
                                .contentType(MediaType.APPLICATION_JSON)          // Tell the server it's in JSON format.
                )
                .andDo(print())                                                     // Print results to console.
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"operand1\":82,\"operand2\":18,\"operations\":\"add\",\"result\":100}"));
    }

    @Test
    public void shouldBeSubtractingTwoOperands() throws Exception {
        // ARRANGE
        MathSolution solution1 = new MathSolution(82, 18);
        solution1.setOperations("subtract");
        solution1.add();

        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(solution1);

        // ACT
        mockMvc.perform(
                        post("/subtract")                                    // Perform the POST request.
                                .content(inputJson)                               // Set the request body.
                                .contentType(MediaType.APPLICATION_JSON)          // Tell the server it's in JSON format.
                )
                .andDo(print())                                                     // Print results to console.
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"operand1\":82,\"operand2\":18,\"operations\":\"subtract\",\"result\":64}"));
    }

    @Test
    public void shouldBeMultiplyingTwoOperands() throws Exception {
        // ARRANGE
        MathSolution solution1 = new MathSolution(82, 18);
        solution1.setOperations("multiply");
        solution1.add();

        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(solution1);

        // ACT
        mockMvc.perform(
                        post("/multiply")                                    // Perform the POST request.
                                .content(inputJson)                               // Set the request body.
                                .contentType(MediaType.APPLICATION_JSON)          // Tell the server it's in JSON format.
                )
                .andDo(print())                                                     // Print results to console.
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"operand1\":82,\"operand2\":18,\"operations\":\"multiply\",\"result\":1476}"));
    }

    @Test
    public void shouldBeDividingTwoOperands() throws Exception {
        // ARRANGE
        MathSolution solution1 = new MathSolution(82, 18);
        solution1.setOperations("divide");
        solution1.add();

        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(solution1);

        // ACT
        mockMvc.perform(
                        post("/divide")                                    // Perform the POST request.
                                .content(inputJson)                               // Set the request body.
                                .contentType(MediaType.APPLICATION_JSON)          // Tell the server it's in JSON format.
                )
                .andDo(print())                                                     // Print results to console.
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"operand1\":82,\"operand2\":18,\"operations\":\"divide\",\"result\":4}"));
    }

    @Test
    public void shouldReturn422CodeIfAddingAndOperandIsNotANumber() throws Exception {
        // ARRANGE
        MathSolution solution1 = new MathSolution();

        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(solution1);

        // ACT
        mockMvc.perform(
                        post("/add")                                // Perform the POST request.
                                .content(inputJson)                               // Set the request body.
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"operand1\":\"two\",\"operand2\":2}")// Tell the server it's in JSON format.
                )
                .andDo(print())                                           // Print results to console.
                .andExpect(status().isUnprocessableEntity());             // ASSERT (status code is 422)
    }

    @Test
    public void shouldReturn422CodeIfSubtractingAndOperandIsNotANumber() throws Exception {
        // ARRANGE
        MathSolution solution1 = new MathSolution();

        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(solution1);

        // ACT
        mockMvc.perform(
                        post("/subtract")                                // Perform the POST request.
                                .content(inputJson)                               // Set the request body.
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"operand1\":\"one\",\"operand2\":2}")// Tell the server it's in JSON format.
                )
                .andDo(print())                                           // Print results to console.
                .andExpect(status().isUnprocessableEntity());             // ASSERT (status code is 422)
    }

    @Test
    public void shouldReturn422CodeIfMultiplyingAndOperandIsNotANumber() throws Exception {
        // ARRANGE
        MathSolution solution1 = new MathSolution();

        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(solution1);

        // ACT
        mockMvc.perform(
                        post("/multiply")                                // Perform the POST request.
                                .content(inputJson)                               // Set the request body.
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"operand1\":\"three\",\"operand2\":2}")// Tell the server it's in JSON format.
                )
                .andDo(print())                                           // Print results to console.
                .andExpect(status().isUnprocessableEntity());             // ASSERT (status code is 422)
    }

    @Test
    public void shouldReturn422CodeIfDividingAndOperandIsNotANumber() throws Exception {
        // ARRANGE
        MathSolution solution1 = new MathSolution();

        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(solution1);

        // ACT
        mockMvc.perform(
                        post("/divide")                                // Perform the POST request.
                                .content(inputJson)                               // Set the request body.
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"operand1\":\"four\",\"operand2\":\"one\"}")// Tell the server it's in JSON format.
                )
                .andDo(print())                                           // Print results to console.
                .andExpect(status().isUnprocessableEntity());             // ASSERT (status code is 422)
    }



    @Test
    public void shouldReturn422ErrorIfTheOperand2IsZero() throws Exception {
        // ARRANGE
        MathSolution solution1 = new MathSolution(9,0);

        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(solution1);

        // ACT
        mockMvc.perform(
                        post("/divide")                                    // Perform the POST request.
                                .content(inputJson)                               // Set the request body.
                                .contentType(MediaType.APPLICATION_JSON)          // Tell the server it's in JSON format.
                )
                .andDo(print())                                                     // Print results to console.
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void shouldReturn422StatusIfMissingOperandOrIfOperandsAreNotBothNumbersAdd() throws Exception {

        // ARRANGE
        MathSolution inputOperation = new MathSolution();
        inputOperation.setOperand1(1);
        inputOperation.setOperand2(1780);

        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(inputOperation);


        // ACT
        mockMvc.perform(
                        post("/add")                                // Perform the POST request.
                                .content(inputJson)                               // Set the request body.
                                .contentType(MediaType.APPLICATION_JSON)          // Tell the server it's in JSON format.
                )
                .andDo(print())                                                     // Print results to console.
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void shouldReturn422StatusIfMissingOperandOrIfOperandsAreNotBothNumbersSubtract () throws Exception {

        // ARRANGE
        MathSolution inputOperation = new MathSolution();
        inputOperation.setOperand1(8);
        inputOperation.setOperand2(1700);

        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(inputOperation);


        // ACT
        mockMvc.perform(
                        post("/subtract")                                // Perform the POST request.
                                .content(inputJson)                               // Set the request body.
                                .contentType(MediaType.APPLICATION_JSON)          // Tell the server it's in JSON format.
                )
                .andDo(print())                                                     // Print results to console.
                .andExpect(status().isUnprocessableEntity());

    }
    @Test
    public void shouldReturn422StatusIfMissingOperandOrIfOperandsAreNotBothNumbersMultiply() throws Exception {

        // ARRANGE
        MathSolution inputOperation = new MathSolution();
        inputOperation.setOperand1(1);
        inputOperation.setOperand2(1780);

        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(inputOperation);


        // ACT
        mockMvc.perform(
                        post("/multiply")                                // Perform the POST request.
                                .content(inputJson)                               // Set the request body.
                                .contentType(MediaType.APPLICATION_JSON)          // Tell the server it's in JSON format.
                )
                .andDo(print())                                                     // Print results to console.
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void shouldReturn422StatusIfMissingOperandOrIfOperandsAreNotBothNumbersDivide() throws Exception {

        // ARRANGE
        MathSolution inputOperation = new MathSolution();
        inputOperation.setOperand1(1);
        inputOperation.setOperand2(1780);

        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(inputOperation);


        // ACT
        mockMvc.perform(
                        post("/divide")                                // Perform the POST request.
                                .content(inputJson)                               // Set the request body.
                                .contentType(MediaType.APPLICATION_JSON)          // Tell the server it's in JSON format.
                )
                .andDo(print())                                                     // Print results to console.
                .andExpect(status().isUnprocessableEntity());

    }



}