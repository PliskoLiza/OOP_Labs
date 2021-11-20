package lab3.test;

import lab3.logic.*;

import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args)
    {
        MutableMatrix massive_1 = MutableMatrix.Random_Filling(2, 3);
        Display_Matrix(massive_1);
        System.out.println();

        ImmutableMatrix massive_1_copy = new ImmutableMatrix(massive_1);
        Display_Matrix(massive_1_copy);

        System.out.println(massive_1.equals(massive_1_copy));

        System.out.println(massive_1_copy.Get_Element(0, 1));

        /*Display_Massive(massive_1.Get_Line(1));
        System.out.println();
        Display_Massive(massive_1.Get_Column(1));
*/
        /*MutableMatrix transposed_massive_1 = massive_1.Transpose_Matrix();

        Display_Matrix(transposed_massive_1);
        System.out.println();
*/
    //    massive_1.Set_Element(2, 2, 0.0f);


    }

    public static void Display_Matrix(Matrix matrix)
    {
        for (int line = 0; line < matrix.lines_count(); line++)
        {
            for (int column = 0; column < matrix.Get_Line(0).length; column++)
            {
                System.out.printf("%.2f", matrix.Get_Element(line, column));
                System.out.print("  ");
            }
            System.out.println();
        }
    }

    public static void Display_Massive(Float[] massive)
    {
        for (Float element : massive) {
            System.out.print(new DecimalFormat("0.00").format(element));
            System.out.print("  ");
        }
    }
}

