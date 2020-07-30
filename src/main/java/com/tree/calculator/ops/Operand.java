package com.tree.calculator.ops;

public interface Operand<T, R> {

    R evaluate();

    String toString();

}
