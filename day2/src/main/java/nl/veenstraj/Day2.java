package nl.veenstraj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day2 {
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
            int totalPower = 0;
            while (line != null) {

                int idxGameNrEnd = line.indexOf(":");
                int gamenr = Integer.parseInt(line.substring(5, idxGameNrEnd));

                String[] reveals = line.substring(idxGameNrEnd + 1).split(";");
                int minimalRed = 0;
                int minimalGreen = 0;
                int minimalBlue = 0;
                boolean validGame = true;
                for (String reveal : reveals) {
                    int number = bepaalNumber("red", reveal);
                    if (number > minimalRed) minimalRed = number;
                    if (number > MAX_RED) validGame = false;

                    number = bepaalNumber("green", reveal);
                    if (number > minimalGreen) minimalGreen = number;
                    if (number > MAX_GREEN) validGame = false;

                    number = bepaalNumber("blue", reveal);
                    if (number > minimalBlue) minimalBlue = number;
                    if (number > MAX_BLUE) validGame = false;

                }
                if (validGame) sum += gamenr;
                int power = minimalRed * minimalGreen * minimalBlue;
                totalPower += power;
                System.out.println("game: " + gamenr + (validGame ? " is valide " : " is INvalide ") + "sum = " + sum + " power = " + power);
                // read next line
                line = reader.readLine();
            }
            System.out.println("sum=" + sum + ", totalpower=" + totalPower);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static int bepaalNumber(String colour, String reveal) {
        int idxColour = reveal.indexOf(colour);
        if (idxColour > 0) {
            return Integer.parseInt(reveal.substring(idxColour - 3, idxColour).trim());
        }
        return 0;
    }
}
