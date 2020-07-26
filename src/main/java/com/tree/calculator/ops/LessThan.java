package com.tree.calculator.ops;

import com.tree.calculator.Calculator;

public class LessThan implements Operand<Double, Boolean> {

    private final Operand<Double, Double> operand1;
    private final Operand<Double, Double> operand2;

    public LessThan(Operand<Double, Double> operand1,
                    Operand<Double, Double> operand2) {
        if (operand1 == null ||
                operand2 == null) {
            throw new IllegalArgumentException("Minimum of two operands");
        }

        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    @Override
    public Boolean evaluate() {

        double operand1;
        double operand2;

        if (this.operand1 instanceof Value) {
            operand1 = this.operand1.evaluate();
        } else {
            operand1 = Calculator.calc(this.operand1);
        }

        if (this.operand2 instanceof Value) {
            operand2 = this.operand2.evaluate();
        } else {
            operand2 = Calculator.calc(this.operand2);
        }

        return operand1 < operand2;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("( ");

        if (operand1 instanceof Value) {
            result.append(operand1.toString());
        } else {
            result.append(Calculator.operandToStringBuilder(operand1));
        }

        result.append(" < ");

        if (operand2 instanceof Value) {
            result.append(operand2.toString());
        } else {
            result.append(Calculator.operandToStringBuilder(operand2));
        }

        return result.append(" )").toString();
    }

}
