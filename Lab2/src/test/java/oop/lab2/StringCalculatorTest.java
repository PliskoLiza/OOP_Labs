package oop.lab2;

import org.junit.Assert;
import org.junit.Test;

public class StringCalculatorTest
{
    @Test
    public void Test1()
    {
        Assert.assertEquals(3, StringCalculator.Add("1,2"));
        Assert.assertEquals(1, StringCalculator.Add("1"));
        Assert.assertEquals(0, StringCalculator.Add(""));
    }
}
