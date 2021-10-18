package oop.lab2;


import java.util.ArrayList;

public class StringCalculator
{
    public static int Add(String numbers)
    {
        int sum = 0, i = 0;
        StringBuilder number = new StringBuilder();
        ArrayList<Character> delimiters = new ArrayList<>();
        delimiters.add(',');
        delimiters.add('\n');
        if (numbers.length() != 0)
        {
            if (numbers.startsWith("//"))
            {
                delimiters.clear();
                delimiters.add(numbers.charAt(2));
                i = 4;
            }
            for (; i < numbers.length(); i++)
            {
                if (delimiters.contains(numbers.charAt(i)))
                {
                    sum += Integer.parseInt(number.toString());
                    number.delete(0, number.length());
                }
                else number.append(numbers.charAt(i));
            }
            sum += Integer.parseInt(number.toString());
        }
        return sum;
    }
}
