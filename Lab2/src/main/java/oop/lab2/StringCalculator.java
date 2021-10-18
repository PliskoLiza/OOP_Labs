package oop.lab2;


public class StringCalculator
{
    public static int Add(String numbers)
    {
        if(numbers.length() == 0) return 0;
        else
        {
            int delimiters_count = 0;
            int delimiter_place = 0;
            for (int i = 0; i < numbers.length(); i++)
            {
                if (numbers.charAt(i) == ',') {delimiters_count ++; delimiter_place = i;}
            }
            if (delimiters_count != 0 && delimiter_place != 0)
            {
                int first = Integer.parseInt(numbers.substring(0, delimiter_place));
                int second = Integer.parseInt(numbers.substring(delimiter_place + 1));
                return first + second;
            }
            else return Integer.parseInt(numbers);
        }
    }
}
