package nl.veenstraj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day1 {
    static int index = 99;

    public static void main(String[] args) {
        //readLinePart1();
        //readLinePart2();
        readLinePart2B();
    }


    public static void readLinePart1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day1/target/classes/input.txt"));
            String line = reader.readLine();

            int sum = 0;
            while (line != null) {
                String digits = line.replaceAll("[a-zA-Z]", "");
                int digit1 = Integer.parseInt(digits.substring(0, 1)) * 10;
                int digit2 = Integer.parseInt(digits.substring(digits.length() - 1));
                sum += digit1 + digit2;
                System.out.println(line + "\t " + digits + "\t " + (digit1 + digit2) + "\t " + sum);
                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void readLinePart2() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day1/target/classes/input.txt"));
            String line = reader.readLine();

            int sum = 0;
            while (line != null) {
                String line2 = line;
                String line1;
                do {
                    line1 = line2;
                    line2 = replaceWrittenNumbers(line1);
                } while (!line1.equals(line2));
                String digits = line2.replaceAll("[a-zA-Z]", "");
                int digit1 = Integer.parseInt(digits.substring(0, 1)) * 10;
                int digit2 = Integer.parseInt(digits.substring(digits.length() - 1));
                sum += digit1 + digit2;
                System.out.println(line + "\t " + digits + "\t " + (digit1 + digit2) + "\t " + sum);
                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void readLinePart2B() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day1/target/classes/input.txt"));
            String line = reader.readLine();

            int sum = 0;
            while (line != null) {
                String line2 = replaceWrittenNumbers(line);
                String digits = line2.replaceAll("[a-zA-Z]", "");
                int digit1 = Integer.parseInt(digits.substring(0, 1)) * 10;

                line2 = replaceWrittenNumbersBackward(line);
                digits = line2.replaceAll("[a-zA-Z]", "");
                int digit2 = Integer.parseInt(digits.substring(digits.length() - 1));
                sum += digit1 + digit2;
                System.out.println(line + "\t " + digits + "\t " + (digit1 + digit2) + "\t " + sum);
                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static String replaceWrittenNumbers(String line) {
        index = 99; // global var
        String temp;
        String result = line;
        
        if ((temp = bepaalPositie(line, "one", "1")) != null) result = temp;
        if ((temp = bepaalPositie(line, "two", "2")) != null) result = temp;
        if ((temp = bepaalPositie(line, "three", "3")) != null) result = temp;
        if ((temp = bepaalPositie(line, "four", "4")) != null) result = temp;
        if ((temp = bepaalPositie(line, "five", "5")) != null) result = temp;
        if ((temp = bepaalPositie(line, "six", "6")) != null) result = temp;
        if ((temp = bepaalPositie(line, "seven", "7")) != null) result = temp;
        if ((temp = bepaalPositie(line, "eight", "8")) != null) result = temp;
        if ((temp = bepaalPositie(line, "nine", "9")) != null) result = temp;
        return result;
    }

    private static String bepaalPositie(String line, String tekst, String digit) {
        if (line.indexOf(tekst) >= 0 && line.indexOf(tekst) < index) {
            index = line.indexOf(tekst);
            return line.replaceFirst(tekst, digit);
        }
        return null;
    }

    private static String replaceWrittenNumbersBackward(String line) {
        index = -1; // global var
        String temp;
        String result = line;

        if ((temp = bepaalPositieBackward(line, "one", "1")) != null) result = temp;
        if ((temp = bepaalPositieBackward(line, "two", "2")) != null) result = temp;
        if ((temp = bepaalPositieBackward(line, "three", "3")) != null) result = temp;
        if ((temp = bepaalPositieBackward(line, "four", "4")) != null) result = temp;
        if ((temp = bepaalPositieBackward(line, "five", "5")) != null) result = temp;
        if ((temp = bepaalPositieBackward(line, "six", "6")) != null) result = temp;
        if ((temp = bepaalPositieBackward(line, "seven", "7")) != null) result = temp;
        if ((temp = bepaalPositieBackward(line, "eight", "8")) != null) result = temp;
        if ((temp = bepaalPositieBackward(line, "nine", "9")) != null) result = temp;
        return result;
    }

    private static String bepaalPositieBackward(String line, String tekst, String digit) {
        if (line.lastIndexOf(tekst) >= 0 && line.lastIndexOf(tekst) > index) {
            index = line.lastIndexOf(tekst);
            StringBuilder builder = new StringBuilder();
            builder.append(digit);
            builder.append(line.substring(index + tekst.length()));
            return builder.toString();
        }
        return null;
    }
}
