package com.tree.calculator;

import com.tree.calculator.ops.*;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Calculator {

    public static void main(String[] args) {

        Multiplication multiplication = new Multiplication(new Value(3D), new Value(1D));
        Multiplication multiplication2 = new Multiplication(new Value(3D), new Value(1D));
        Multiplication multiplication3 = new Multiplication(multiplication2, multiplication, new Value(1D));

        Sum sum2 = new Sum(multiplication3, new Value(2D), new Value(3D));
        Sum sum3 = new Sum(sum2, new Value(4D), new Value(5D));
        Sum sum4 = new Sum(sum2, sum3, new Value(5D), new Value(6D));

        System.out.println(sum4.toString() + " = " + sum4.evaluate());

        Subtraction sub1 = new Subtraction(new Value(5D), new Value(0D));
        Subtraction sub2 = new Subtraction(sum4, sub1);
        Subtraction sub3 = new Subtraction(sub2, new Value(5D), new Value(3D), new Value(0D));

        Double result = sub3.evaluate();

        System.out.println(result);

        Division div1 = new Division(new Value(1D), sum2);
        Division div2 = new Division(new Value(10D), div1);
        Division div3 = new Division(new Value(100D), div2);

        System.out.println(div3.toString() + " = " + div3.evaluate());

        Sum sum5 = new Sum(new Value(4D), new Value(5D));
        Sum sum6 = new Sum(sum5, new Value(1D), new Value(1D));

        LessThan lessThan = new LessThan(sum5, sum6);
        System.out.println(lessThan.toString() + " is " + lessThan.evaluate());

        GreaterThan greaterThan = new GreaterThan(sum5, sum6);
        System.out.println(greaterThan.toString() + " is " + greaterThan.evaluate());
    }

    public static Double calc(Operand<Double, Double> op) {

        if (op instanceof Sum) {
            return ((Sum) op)
                    .getOperands()
                    .stream()
                    .mapToDouble(Operand::evaluate)
                    .sum();
        }

        if (op instanceof Multiplication) {
            return ((Multiplication) op)
                    .getOperands()
                    .stream()
                    .mapToDouble(Operand::evaluate)
                    .reduce(Multiplication.INIT, (a, b) ->
                            (Double.MAX_VALUE == a || Double.MAX_VALUE == b) ?
                                    Double.MAX_VALUE : a * b);
        }

        if (op instanceof Subtraction) {
            return ((Subtraction) op)
                    .getOperands()
                    .stream()
                    .mapToDouble(Operand::evaluate)
                    .reduce(Double.MIN_VALUE, (a, b) ->
                            Double.MIN_VALUE == a ? b : a - b);
        }

        if (op instanceof Division) {
            double divisor = ((Division) op).getDivisor().evaluate();

            checkDivisor(new Value(divisor));

            double dividend = ((Division) op).getDividend().evaluate();

            return dividend / divisor;
        }

        throw new NoSuchElementException();
    }

    public static StringBuilder operandToStringBuilder(Operand<Double, Double> operand) {
        StringBuilder result = new StringBuilder("(");

        if (operand instanceof Sum) {
            appendSymbol(((Sum) operand).getOperands().iterator(),
                    result,
                    "+");
        } else if (operand instanceof Multiplication) {
            appendSymbol(((Multiplication) operand).getOperands().iterator(),
                    result,
                    "*");
        } else if (operand instanceof Subtraction) {
            appendSymbol(((Subtraction) operand).getOperands().iterator(),
                    result,
                    "-");
        } else if (operand instanceof Division) {
            String symbol = "/";

            String dividend = ((Division) operand).getDividend().toString();
            String divisor = ((Division) operand).getDivisor().toString();

            result.append(dividend).append(symbol).append(divisor);
        } else {
            throw new NoSuchElementException();
        }

        return result.append(")");
    }

    private static void appendSymbol(Iterator<Operand<Double, Double>> it,
                                     StringBuilder result,
                                     String symbol) {
        while (it.hasNext()) {
            result.append(it.next().toString());
            if (it.hasNext()) {
                result.append(symbol);
            }
        }
    }

    public static StringBuilder listToStringBuilder(List<Operand<Double, Double>> operands,
                                                    String symbol) {
        StringBuilder result = new StringBuilder("( ");

        Iterator<Operand<Double, Double>> it = operands.iterator();
        while (it.hasNext()) {
            Operand<Double, Double> op = it.next();

            if (op instanceof Value) {
                result.append(op.evaluate());
            } else {
                result.append(operandToStringBuilder(op));
            }

            if (it.hasNext()) {
                result.append(" ").append(symbol).append(" ");
            }
        }

        return result.append(" )");
    }

    public static void checkDivisor(Operand<Double, Double> op) {
        if (op instanceof Value &&
                op.evaluate() == 0D) {
            throw new IllegalArgumentException("Divisor is zero");
        }
    }

}
