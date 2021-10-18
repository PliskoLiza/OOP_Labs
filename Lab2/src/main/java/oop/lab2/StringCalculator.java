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
                if (numbers.charAt(i) != ',') {number.append(numbers.charAt(i));}
                else {sum += Integer.parseInt(number.toString()); number.delete(0, number.length());}
            }
            sum += Integer.parseInt(number.toString());
        }
        return sum;
    }
}
