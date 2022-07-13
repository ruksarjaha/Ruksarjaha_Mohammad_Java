package com.company.M2ChallengeMohammad.controller;

import com.company.M2ChallengeMohammad.Model.MathSolution;
import com.company.M2ChallengeMohammad.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MathSolutionController {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public MathSolution addTwoInt(@RequestBody @Valid MathSolution operation ) {//MathSolution obj that returns a Json obj
        operation.setOperations("add"); //set the operation and call the method
        operation.add();//calling the add method
        return operation;

    }

    @RequestMapping(value = "/subtract", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public MathSolution subTwoInt(@RequestBody @Valid MathSolution operation ) {
        operation.setOperations("subtract");
        operation.subtract();
        return operation;

    }

    @RequestMapping(value = "/multiply", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public MathSolution mulTwoInt(@RequestBody @Valid MathSolution operation ) {
        operation.setOperations("multiply");
        operation.multiply();
        return operation;
    }

    @RequestMapping(value = "/division", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public MathSolution divTwoInt(@RequestBody @Valid MathSolution operation) {
        operation.setOperations("division");
        operation.divide();

        if(operation.getOperand2()==0) {
            throw new ArithmeticException("You cannot divide by zero");
        } else {
            operation.setOperations("divide");
            operation.divide();
        }
        return operation;

    }

    @RequestMapping(value = "/divide", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public MathSolution divideMath (@RequestBody @Valid MathSolution solution) throws Exception {

        if(solution.getOperand2()==0) {
            throw new ArithmeticException("You cannot divide by zero");
        } else {
            solution.setOperations("divide");
            solution.divide();
        }

        return solution;
    }
}
