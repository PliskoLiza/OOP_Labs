package oop.lab2;


public class StringCalculator
{
    public static int Add(String numbers)
    {
        int sum = 0;
        StringBuilder number = new StringBuilder();
        if (numbers.length() != 0)
        {
            for (int i = 0; i < numbers.length(); i++)
            {
                if (numbers.charAt(i) == ',' || numbers.charAt(i) == '\n')
                {
                    sum += Integer.parseInt(number.toString());
                    number.delete(0, number.length());
                }
                else
                {
                    number.append(numbers.charAt(i));
                }
            }
            sum += Integer.parseInt(number.toString());
        }
        return sum;
    }
}
