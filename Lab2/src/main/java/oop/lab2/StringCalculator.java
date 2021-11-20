package oop.lab2;

import java.util.LinkedList;

public class StringCalculator
{
    public static float Add(String numbers)
    {
        float sum = 0;
        int i = 0;
        StringBuilder number = new StringBuilder();
        LinkedList<String> delimiters;
        LinkedList<Float> negative_numbers = new LinkedList<>();
        String Found_delimiter;
        if (numbers.length() != 0)
        {
            float num;
            if ((delimiters = Find_Delimiter(numbers)).size() == 0)
            {
                delimiters.add(",");
                delimiters.add("\n");
            }
            else {i = Integer.parseInt(delimiters.get(delimiters.size() - 1)); delimiters.removeLast();}

            for (; i < numbers.length(); i++)
            {
                if (!(Found_delimiter = recognize_delimiter(delimiters, numbers, i)).equals(""))
                {
                    i = pass_delimiter(i, Found_delimiter);
                    num = Float.parseFloat(number.toString());
                    if (num <= 1000) sum += num;
                    if (num < 0) negative_numbers.add(num);
                    number.delete(0, number.length());
                }
                else number.append(numbers.charAt(i));
            }
            num = Float.parseFloat(number.toString());
            if (num < 0) negative_numbers.add(num);
            if (num <= 1000) sum += num;
        }
        if (negative_numbers.size() != 0)
        {
            String Error_Message = "Negatives not allowed: ";
            for (Float negatives: negative_numbers)
            {
                Error_Message = String.join(" ", Error_Message, negatives.toString());
            }
            throw new IllegalArgumentException(Error_Message);
        }
        return sum;
    }

    public static String recognize_delimiter(LinkedList<String> delimiters, String numbers, int place)
    {
        StringBuilder Delimiter_Buffer = new StringBuilder();
        int Full_Match = 0;
        int Partial_Match = 0;

        for (int i = place; i < numbers.length(); i++)
        {
            Delimiter_Buffer.append(numbers.charAt(i));
            for (String delimiter: delimiters)
            {
                if (delimiter.startsWith(Delimiter_Buffer.toString())) Partial_Match += 1;
                if (delimiter.equals(Delimiter_Buffer.toString())) Full_Match +=1;
            }
            if (!repeat_recognizing(Full_Match, Partial_Match))
            {
                Delimiter_Buffer.deleteCharAt(Delimiter_Buffer.length() - 1);
                return Delimiter_Buffer.toString();
            }
            else {Partial_Match = 0; Full_Match = 0;}
        }
        return Delimiter_Buffer.toString();
    }

    public static boolean repeat_recognizing(int Full_Match, int Partial_Match)
    {
        return Full_Match != 0 || Partial_Match != 0;
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
                if (numbers.charAt(i) == '[') Delimiter_Buffer.delete(0, Delimiter_Buffer.length());
                else if (numbers.charAt(i) == ']') delimiter.add(String.valueOf(Delimiter_Buffer));
                else if (numbers.charAt(i) != '\n' || (numbers.charAt(i) == '\n') && numbers.charAt(i - 1) != ']') Delimiter_Buffer.append(numbers.charAt(i));
                else
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