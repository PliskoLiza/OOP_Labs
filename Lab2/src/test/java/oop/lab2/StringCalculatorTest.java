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
    @Test
    public void Test2()
    {
        Assert.assertEquals(154, StringCalculator.Add("123,2,4,5,20"));
    }
}
