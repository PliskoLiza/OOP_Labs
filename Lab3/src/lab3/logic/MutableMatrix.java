package lab3.logic;

import java.util.Arrays;

public class MutableMatrix extends Matrix
{
    public MutableMatrix() {super();}

    public MutableMatrix(int lines, int columns) {super(lines, columns);}

    public MutableMatrix(Matrix original)
    {
        super(original);
    }

    public void filling_matrix(Float[][] matrix_1)
    {
        for (int l = 0; l < matrix_1.length; l++)
        {
            for (int c = 0; c < matrix_1[0].length; c++)
            {
                matrix[l][c] = matrix_1[l][c];
            }
        }
    }

    public static MutableMatrix Random_Column(int lines)
    {
        MutableMatrix matrix = new MutableMatrix(lines, 1);
        R_Column(lines, matrix);
        return matrix;
    }

    public void Set_Element(int line, int column, Float element)
    {
        line--;
        column--;
        matrix[line][column] = element;
    }

    public void Set_Line(int line, Float[] chosen_line)
    {
        line--;
        matrix[line] = chosen_line.clone();
    }

    public void Set_Column(int column, Float[] chosen_column)
    {
        column--;
        for (int l = 0; l < matrix.length; l++)
        {
            matrix[l][column] = chosen_column[l];
        }
    }

    public static MutableMatrix Random_Filling(int lines, int columns)
    {
        MutableMatrix matrix = new MutableMatrix(lines, columns);
        R_Filling(lines, columns, matrix);
        return matrix;
    }

    public MutableMatrix Transpose_Matrix()
    {
        MutableMatrix transposed_matrix = new MutableMatrix(matrix[0].length, matrix.length);
        T_Matrix(transposed_matrix);
        return transposed_matrix;
    }
}
