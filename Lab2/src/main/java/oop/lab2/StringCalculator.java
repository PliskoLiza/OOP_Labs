package oop.lab2;


import java.util.ArrayList;
import java.util.LinkedList;

public class StringCalculator
{
    public static int Add(String numbers)
    {
        int sum = 0, i = 0;
        StringBuilder number = new StringBuilder();
        ArrayList<Character> delimiters = new ArrayList<>(2);
        LinkedList<Integer> negative_numbers = new LinkedList<>();
        if (numbers.length() != 0)
        {
            int num;
            if (numbers.startsWith("//"))
            {
                delimiters.add(numbers.charAt(2));
                i = 4;
            }
            else
            {
                delimiters.add(',');
                delimiters.add('\n');
            }
            for (; i < numbers.length(); i++)
            {
                if (delimiters.contains(numbers.charAt(i)))
                {
                    num = Integer.parseInt(number.toString());
                    sum += num;
                    if (num < 0) negative_numbers.add(num);
                    number.delete(0, number.length());
                }
                else number.append(numbers.charAt(i));
            }
            num = Integer.parseInt(number.toString());
            if (num < 0) negative_numbers.add(num);
            sum += num;
        }
        if (negative_numbers.size() != 0)
        {
            String Error_Message = "Negatives not allowed: ";
            for (Integer negatives: negative_numbers)
            {
                Error_Message = String.join(" ", Error_Message, negatives.toString());
            }
            throw new IllegalArgumentException(Error_Message);
        }
        return sum;
    }
}
