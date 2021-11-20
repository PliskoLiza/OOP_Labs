package oop.lab2;

import org.junit.Assert;
import org.junit.Test;


public class StringCalculatorTest
{
    @Test
    public void Test1()
    {
        Assert.assertEquals(3.2, StringCalculator.Add("1.2,2"), 0.00001);
        Assert.assertEquals(1.0, StringCalculator.Add("1"), 0.00001);
        Assert.assertEquals(0.0, StringCalculator.Add(""), 0.00001);
    }

    @Test
    public void Test2()
    {
        Assert.assertEquals(154.2333, StringCalculator.Add("123.2333,2,4,5,20"), 0.00001);
    }

    @Test
    public void Test3()
    {
        Assert.assertEquals(6.0, StringCalculator.Add("1\n2,3"), 0.00001);
    }

    @Test
    public void Test4()
    {
        Assert.assertEquals(3, StringCalculator.Add("//;\n1;2"), 0.00001);
    }

    @Test
    public void Test5() throws IllegalArgumentException
    {
        try {
            StringCalculator.Add("-123,2\n-4,5,-20");
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException thrown)
        {
            Assert.assertEquals("Negatives not allowed:  -123.0 -4.0 -20.0", thrown.getMessage());
        }
    }

    @Test
    public void Test6()
    {
        Assert.assertEquals(2.0, StringCalculator.Add("1001,2"), 0.00001);
        Assert.assertEquals(154.0, StringCalculator.Add("123\n2,4\n1001,5,20"), 0.00001);
    }

    @Test
    public void Test7()
    {
        Assert.assertEquals(6.0, StringCalculator.Add("//[***]\n1***2***3"), 0.00001);
    }

    @Test
    public void Test8()
    {
        Assert.assertEquals(10.0, StringCalculator.Add("//[*][**][**&*]\n1**2*3**&*4"), 0.00001);
        Assert.assertEquals(1015.0, StringCalculator.Add("//[**][++][%\n@]\n1**2++3**1001++1000%\n@4++5"), 0.00001);
    }

    @Test
    public void Test()
    {
        System.out.println(StringCalculator.Add("//[,][**][,,]\n1,,2,1001**1002"));
    }
}
