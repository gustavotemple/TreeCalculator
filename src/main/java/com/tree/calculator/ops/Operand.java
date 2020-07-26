package com.tree.calculator.ops;

public interface Operand<T, R> {

    public R evaluate();

    public String toString();

}
