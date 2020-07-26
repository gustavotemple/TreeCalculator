package com.tree.calculator.ops;

public class Value implements Operand<Double, Double> {

    private final Double value;

    public Value(Double value) {
        if (value == null ||
                Double.isInfinite(value) ||
                Double.isNaN(value)) {
            throw new IllegalArgumentException("Illegal value");
        }

        this.value = value;
    }

    @Override
    public Double evaluate() {
        return value;
    }

    @Override
    public String toString() {
        return " " + value + " ";
    }
}
