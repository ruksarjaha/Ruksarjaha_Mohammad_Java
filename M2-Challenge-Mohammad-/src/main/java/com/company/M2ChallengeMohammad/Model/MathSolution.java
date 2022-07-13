package com.company.M2ChallengeMohammad.Model;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class MathSolution {

    @NotNull(message = "operand1  requires a value")
    @Max(1570) // MAx value for operand1 is 1570
    private int operand1;

    @NotNull(message = "operand2  requires a value")
    @Max(1670) //Max value for operand2 is 1670
    private int operand2;

    private String operations;

    private int result;

    public MathSolution(int operand1, int operand2, String operations, int result) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operations = operations;
        this.result = result;
    }

    public MathSolution() {
    }

    public MathSolution(int operand1, int operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public int getOperand1() {
        return operand1;
    }

    public void setOperand1(int operand1) {
        this.operand1 = operand1;
    }

    public int getOperand2() {
        return operand2;
    }

    public void setOperand2(int operand2) {
        this.operand2 = operand2;
    }

    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MathSolution)) return false;
        MathSolution that = (MathSolution) o;
        return getOperand1() == that.getOperand1() && getOperand2() == that.getOperand2() && getResult() == that.getResult() && Objects.equals(getOperations(), that.getOperations());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperand1(), getOperand2(), getOperations(), getResult());
    }

    @Override
    public String toString() {
        return "MathSolution{" +
                "operand1=" + operand1 +
                ", operand2=" + operand2 +
                ", operations='" + operations + '\'' +
                ", result=" + result +
                '}';
    }


    public int add() {

        if(operations.equals("add")){
            result = operand1 + operand2;
        }
        return result;

    }

    public int subtract() {

        if(operations.equals("subtract")){
            result = operand1 - operand2;
        }
        return result;

    }

    public int multiply() {

        if(operations.equals("multiply")){
            result = operand1 * operand2;
        }
        return result;

    }

    public int divide() {

        if(operations.equals("divide")){
            result = operand1 / operand2;
        }
        return result;

    }
}
