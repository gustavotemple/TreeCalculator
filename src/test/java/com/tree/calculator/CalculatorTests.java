package com.tree.calculator;

import com.tree.calculator.ops.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTests {

    @Test
    public void arithmeticsTest() {
        Multiplication multiplication = new Multiplication(new Value(1D), new Value(2D));
        Multiplication multiplication2 = new Multiplication(new Value(3D), new Value(4D));
        Multiplication multiplication3 = new Multiplication(multiplication2, multiplication, new Value(5D));

        assertEquals(((3D * 4D) * (1D * 2D) * 5D), multiplication3.evaluate());

        Sum sum2 = new Sum(multiplication3, new Value(2D), new Value(3D));
        Sum sum3 = new Sum(sum2, new Value(4D), new Value(5D));
        Sum sum4 = new Sum(sum2, sum3, new Value(5D), new Value(6D));

        assertEquals(((((3.0 * 4.0) * (1.0 * 2.0) * 5.0) + 2.0 + 3.0) + ((((3.0 * 4.0) * (1.0 * 2.0) * 5.0) + 2.0 + 3.0) + 4.0 + 5.0) + 5.0 + 6.0),
                sum4.evaluate());
        assertEquals("( (( ( 3.0 * 4.0 ) * ( 1.0 * 2.0 ) * 5.0 )+ 2.0 + 3.0 ) + (( (( 3.0 * 4.0 )*( 1.0 * 2.0 )* 5.0 ) + 2.0 + 3.0 )+ 4.0 + 5.0 ) + 5.0 + 6.0 ) = 270.0",
                (sum4.toString() + " = " + sum4.evaluate()));

        Subtraction sub1 = new Subtraction(new Value(5D), new Value(0D));
        Subtraction sub2 = new Subtraction(new Sum(new Value(5D), new Value(0D)), sub1);
        Subtraction sub3 = new Subtraction(sub2, new Value(5D), new Value(3D), new Value(0D));

        assertEquals((((5.0 + 0.0) - (5.0 - 0.0)) - 5.0 - 3.0 - 0.0), sub3.evaluate());

        Division div1 = new Division(new Value(10D), new Sum(new Value(5D), new Value(0D)));
        Division div2 = new Division(new Value(10D), div1);
        Division div3 = new Division(new Value(100D), div2);

        assertEquals((100.0 / (10.0 / (10.0 / (5.0 + 0.0)))), div3.evaluate());
    }

    @Test
    public void greaterThanAndLessThanTest() {
        Sum sum5 = new Sum(new Value(4D), new Value(5D));
        Sum sum6 = new Sum(sum5, new Value(1D), new Value(1D));

        assertEquals((4D + 5D), sum5.evaluate());
        assertEquals(((4D + 5D) + 1D + 1D), sum6.evaluate());

        LessThan lessThan = new LessThan(sum5, sum6);
        assertEquals("( ( 4.0 + 5.0 ) < (( 4.0 + 5.0 )+ 1.0 + 1.0 ) ) is true",
                lessThan.toString() + " is " + lessThan.evaluate());

        assertTrue(sum5.evaluate() < sum6.evaluate());

        GreaterThan greaterThan = new GreaterThan(sum5, sum6);
        assertEquals("( ( 4.0 + 5.0 ) > (( 4.0 + 5.0 )+ 1.0 + 1.0 ) ) is false",
                greaterThan.toString() + " is " + greaterThan.evaluate());

        assertFalse(sum5.evaluate() > sum6.evaluate());
    }

}
