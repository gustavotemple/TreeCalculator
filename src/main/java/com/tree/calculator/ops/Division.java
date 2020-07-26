package com.tree.calculator.ops;

import com.tree.calculator.Calculator;

public class Division implements Operand<Double, Double> {

    private final Operand<Double, Double> dividend;
    private final Operand<Double, Double> divisor;

    public Division(Operand<Double, Double> dividend,
                    Operand<Double, Double> divisor) {
        if (dividend == null ||
                divisor == null) {
            throw new IllegalArgumentException("Minimum of two operands");
        }

        Calculator.checkDivisor(divisor);

        this.dividend = dividend;
        this.divisor = divisor;
    }

    @Override
    public Double evaluate() {

        double divisorAux;
        double dividendAux;

        if (divisor instanceof Value) {
            divisorAux = divisor.evaluate();
        } else {
            divisorAux = Calculator.calc(divisor);
        }

        Calculator.checkDivisor(new Value(divisorAux));

        if (dividend instanceof Value) {
            dividendAux = dividend.evaluate();
        } else {
            dividendAux = Calculator.calc(dividend);
        }

        return dividendAux / divisorAux;
    }

    public Operand<Double, Double> getDividend() {
        return dividend;
    }

    public Operand<Double, Double> getDivisor() {
        return divisor;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("( ");

        if (dividend instanceof Value) {
            result.append(dividend.toString());
        } else {
            result.append(Calculator.operandToStringBuilder(dividend));
        }

        result.append(" / ");

        if (divisor instanceof Value) {
            result.append(divisor.toString());
        } else {
            result.append(Calculator.operandToStringBuilder(divisor));
        }

        return result.append(" )").toString();
    }
}
