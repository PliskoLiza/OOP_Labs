package oop.lab2;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.SecureDirectoryStream;

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

    @Test
    public void Test3()
    {
        Assert.assertEquals(6, StringCalculator.Add("1\n2,3"));
    }

    @Test
    public void Test4()
    {
        Assert.assertEquals(3, StringCalculator.Add("//;\n1;2"));
    }

    @Test
    public void Test5() throws IllegalArgumentException
    {
        try {
            StringCalculator.Add("-123,2\n-4,5,-20");
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException thrown)
        {
            Assert.assertEquals("Negatives not allowed:  -123 -4 -20", thrown.getMessage());
        }
    }

    @Test
    public void Test6()
    {
        Assert.assertEquals(2, StringCalculator.Add("1001,2"));
        Assert.assertEquals(154, StringCalculator.Add("123\n2,4\n1001,5,20"));
    }

    @Test
    public void Test7()
    {
        Assert.assertEquals(6, StringCalculator.Add("//[***]\n1***2***3"));
    }

    @Test
    public void Test8()
    {
        Assert.assertEquals(10, StringCalculator.Add("//[*][**][**&*]\n1**2*3**&*4"));
    }
}
