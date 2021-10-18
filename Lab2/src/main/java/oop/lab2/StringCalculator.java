package oop.lab2;

import java.util.LinkedList;

public class StringCalculator
{
    public static int Add(String numbers)
    {
        int sum = 0, i = 0;
        StringBuilder number = new StringBuilder();
        LinkedList<String> delimiters;
        LinkedList<Integer> negative_numbers = new LinkedList<>();
        if (numbers.length() != 0)
        {
            int num;
            if ((delimiters = Find_Delimiter(numbers)).size() == 0)
            {
                delimiters.add(",");
                delimiters.add("\n");
            }
            else {i = Integer.parseInt(delimiters.get(delimiters.size() - 1)); delimiters.removeLast();}

            for (; i < numbers.length(); i++)
            {
                if (delimiters.getFirst().startsWith(String.valueOf(numbers.charAt(i))) || delimiters.getLast().startsWith(String.valueOf(numbers.charAt(i))))
                {
                    i = pass_delimiter(i, delimiters.get(0));
                    num = Integer.parseInt(number.toString());
                    if (num <= 1000) sum += num;
                    if (num < 0) negative_numbers.add(num);
                    number.delete(0, number.length());
                }
                else number.append(numbers.charAt(i));
            }
            num = Integer.parseInt(number.toString());
            if (num < 0) negative_numbers.add(num);
            if (num <= 1000) sum += num;
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

    public static int pass_delimiter(int place, String delimiter)
    {
        place += delimiter.length();
        return place - 1;
    }

    public static LinkedList<String> Find_Delimiter(String numbers)
    {
        StringBuilder Delimiter_Buffer = new StringBuilder();
        LinkedList<String> delimiter = new LinkedList<>();
        if (numbers.startsWith("//["))
        {
            for (int i = 3; i < numbers.length(); i++)
            {
                if (numbers.charAt(i) != ']' && numbers.charAt(i) != '[')
                {
                    Delimiter_Buffer.append(numbers.charAt(i));
                }
                else if (numbers.charAt(i) != '\n')
                {
                    delimiter.add(String.valueOf(Delimiter_Buffer));
                    Delimiter_Buffer.delete(0, Delimiter_Buffer.length());
                }
                if (numbers.charAt(i) == '\n')
                {
                    delimiter.add(String.valueOf(i + 1));
                    return delimiter;
                }
            }
        }
        else if (numbers.startsWith("//"))
        {
            delimiter.add(String.valueOf(numbers.charAt(2)));
            delimiter.add(String.valueOf(4));
        }
        return delimiter;
    }
}
