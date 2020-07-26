package com.tree.calculator.ops;

import com.tree.calculator.Calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Multiplication implements Operand<Double, Double> {

    public static final Double INIT = 1D;
    private final List<Operand<Double, Double>> operands = new ArrayList<>();

    @SafeVarargs
    public Multiplication(Operand<Double, Double>... ops) {
        if (ops == null ||
                ops.length <= 1) {
            throw new IllegalArgumentException("Minimum of two operands");
        }

        Collections.addAll(operands, ops);
    }

    @Override
    public Double evaluate() {

        double result = INIT;

        for (Operand<Double, Double> op : getOperands()) {
            double aux;

            if (op instanceof Value) {
                aux = op.evaluate();
            } else {
                aux = Calculator.calc(op);
            }

            result = result == Double.MAX_VALUE ? result : result * aux;
        }

        return result;
    }

    public List<Operand<Double, Double>> getOperands() {
        return operands;
    }

    @Override
    public String toString() {
        return Calculator.listToStringBuilder(getOperands(),
                "*").toString();
    }

}
