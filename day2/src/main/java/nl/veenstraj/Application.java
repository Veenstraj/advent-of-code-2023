package nl.veenstraj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Application {
    private final static int MAX_RED = 12;
    private final static int MAX_GREEN = 13;
    private final static int MAX_BLUE = 14;

    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day2/target/classes/input.txt"));
            String line = reader.readLine();

            int sum = 0;
            while (line != null) {

                int idxGameNrEnd = line.indexOf(":");
                int gamenr = Integer.parseInt(line.substring(5, idxGameNrEnd));

                String[] reveals = line.substring(idxGameNrEnd + 1).split(";");

                for (String reveal : reveals) {
                    int idxRed = reveal.indexOf("red");
                    if (idxRed > 0) {
                        int nrRed = Integer.parseInt(reveal.substring(idxRed - 3, idxRed).trim());
                        if (nrRed <= MAX_RED) {
                            int idxGreen = reveal.indexOf("green");
                            if (idxGreen > 0) {
                                int nrGreen = Integer.parseInt(reveal.substring(idxGreen - 3, idxGreen).trim());
                                if (nrGreen <= MAX_GREEN) {
                                    int idxBlue = reveal.indexOf("blue");
                                    if (idxBlue > 0) {
                                        int nrBlue = Integer.parseInt(reveal.substring(idxBlue - 3, idxBlue).trim());
                                        if (nrBlue <= MAX_BLUE) {
                                            sum += gamenr;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
