package lab3.logic;

import java.util.Arrays;

final public class ImmutableMatrix extends Matrix
{
    public ImmutableMatrix() {super();}

    public ImmutableMatrix(int lines, int columns) {super(lines, columns);}

    public ImmutableMatrix(Matrix original)
    {
        super(original);
    }

    public static ImmutableMatrix Random_Column(int lines)
    {
        ImmutableMatrix matrix = new ImmutableMatrix(lines, 1);
        R_Column(lines, matrix);
        return matrix;
    }

    public static ImmutableMatrix Random_Filling(int lines, int columns)
    {
        ImmutableMatrix matrix = new ImmutableMatrix(lines, columns);
        R_Filling(lines, columns, matrix);
        return matrix;
    }

    public ImmutableMatrix Transpose_Matrix()
    {
        ImmutableMatrix transposed_matrix = new ImmutableMatrix(matrix[0].length, matrix.length);
        T_Matrix(transposed_matrix);
        return transposed_matrix;
    }
}
