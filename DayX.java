//package

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class DayX {
    private static final String inputfile = "./dayx/target/classes/input.txt";

    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(inputfile));
            String line = reader.readLine();

            int sum = 0;
            while (line != null) {
                if (line.trim().length() > 0) {

                }

                // read next line
                line = reader.readLine();
            }
            reader.close();


            System.out.println("sum=" + sum);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
