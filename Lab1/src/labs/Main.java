package labs;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        //CSV_Reader.reader();
      //  System.out.println("\nnew parsing\n");
      //  CSV_Parser.parser(',');
    }
}

class Reader
{

    public static void read ()
    {
        try (FileReader reader = new FileReader("C:\\Users\\DragonLord\\Desktop\\test_file.csv"))
        {
            int symbol;
            while ((symbol = reader.read()) != -1)
            {
                Analyzer.analyze((char) symbol, reader);
            }
        } catch (IOException except)
        {
            System.out.println(except.getMessage());
        }
    }

}

class Analyzer
{
    int symbol_count = 0;
    int quotes = 0;

    public static void analyze (char symbol, FileReader reader) throws IOException {
        if (symbol == '"')
        {
            while ((symbol = reader.read()) != '\n' && (symbol = reader.read()) != '"')
            {

            }
        }
    }
}

class writer
{

}


/*class CSV_Parser{

    public static void parser(char delimiter) {
        try (FileReader parser = new FileReader("C:\\Users\\DragonLord\\Desktop\\test_file.csv"))
        {
            int symbol;
            int quotes = 0;
            int symbols_count = 0;
            while ((symbol = parser.read()) != -1)
            {
                do {
                    if ((char) symbol == '"') {
                        quotes += 1;
                        if ((char) (symbol = parser.read()) == '"'){
                            symbols_count += 1;
                        }
                    }
                } while ((char) (symbol = parser.read()) != '\n');

            }
        }
        catch (IOException except) {

            System.out.println(except.getMessage());
        }
    }
}

class Writer {

    public static void write(int symbols_count, char delimiter){
        try (FileWriter writer = new FileWriter("C:\\Users\\DragonLord\\Desktop\\test_output_file.txt"))
        {
            writer.write(symbols_count);
            writer.write(delimiter);
            writer.write('\n');
        }
        catch (IOException except) {

            System.out.println(except.getMessage());
        }
    }
}*/





























/*class CSV_Reader {

    public static void reader() {
        try (FileReader reader = new FileReader("C:\\Users\\DragonLord\\Desktop\\test_file.csv"))
        {
            int c;
            while ((c = reader.read()) != -1)
            {
                System.out.print((char) c);
            }

        }
        catch (IOException except) {

            System.out.println(except.getMessage());
        }
    }

}*/