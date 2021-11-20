package lab3.logic;

public interface Matrix_Interface<D>
{
    public int lines_count();
    public D Get_Element(int line, int column);
    public D[] Get_Column(int chosen_column);
    public D[] Get_Line(int chosen_line);
    public int[] Get_Dimensions();
}
