package lab3.logic;

import java.util.Arrays;

abstract public class Matrix implements Matrix_Interface<Float>
{
    protected Float[][] matrix;

    protected Matrix() {
        matrix = new Float[0][0];
    }

    protected Matrix(int lines, int columns) {
        matrix = new Float[lines][columns];
        for (int l = 0; l < lines; l++) {
            for (int c = 0; c < columns; c++) {
                matrix[l][c] = 0.0f;
            }
        }
    }

    protected Matrix(Matrix original) {
        matrix = new Float[original.lines_count()][original.matrix[0].length];

        for (int l = 0; l < original.lines_count(); l++)
        {
            for (int c = 0; c < original.matrix[0].length; c++)
            {
                matrix[l][c] = original.matrix[l][c];
            }
        }
    }

    protected static void R_Filling(int lines, int columns, Matrix matrix) {
        for (int l = 0; l < lines; l++) {
            for (int c = 0; c < columns; c++) {
                matrix.matrix[l][c] = (float) (Math.random() * (100 + 100) - 100);
            }
        }
    }

    protected static void R_Column(int lines, Matrix matrix) {
        for (int l = 0; l < lines; l++) {
            matrix.matrix[l][0] = (float) (Math.random() * (100 + 100) - 100);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if(!(obj instanceof Matrix)) return false;
        if (obj == this) return true;
        Matrix second_matrix = (Matrix) obj;
        if ((matrix.length != second_matrix.matrix.length) ||
                (matrix[0].length != second_matrix.matrix[0].length)) return false;
        for (int l = 0; l < matrix.length; l++)
        {
            for (int c = 0; c < matrix[0].length; c++)
            {
                if (!matrix[l][c].equals(second_matrix.matrix[l][c])) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(matrix);
    }

    public int lines_count() {
        return matrix.length;
    }

    public Float Get_Element(int line, int column) {
        return matrix[line][column];
    }

    public Float[] Get_Column(int chosen_column) {
        Float[] column = new Float[matrix[0].length];
        for (int line = 0; line < matrix.length; line++) {
            column[line] = matrix[line][chosen_column];
        }
        return column;
    }

    public Float[] Get_Line(int chosen_line) {
        Float[] line = new Float[matrix.length];
        for (int l = 0; l < matrix.length; l++) {
            if (l == chosen_line) line = matrix[l].clone();
        }
        return line;
    }

    public int[] Get_Dimensions() {
        int[] dimensions = new int[2];
        dimensions[0] = matrix.length;
        dimensions[1] = matrix[0].length;
        return dimensions;
    }

    protected void T_Matrix(Matrix transposed_matrix)
    {
        for (int l = 0; l < matrix.length; l++)
        {
            for (int c = 0; c < matrix[0].length; c++)
            {
                transposed_matrix.matrix[c][l] = matrix[l][c];
            }
        }
    }
}
