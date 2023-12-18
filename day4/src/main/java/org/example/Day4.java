package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day4 {
    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day4/target/classes/input.txt"));
            String line = reader.readLine();

            int sum = 0;
            while (line != null) {
                String[] cards = line.split(":");
                String[] numbers = cards[1].trim().split("\\|");
                String[] winningNumbers = numbers[0].trim().split(" ");
                String[] myNumbers = numbers[1].trim().split(" ");

                int points = 0;
                System.out.printf(cards[0] + ":");
                for (String winningNumber : winningNumbers) {
                    for (String myNumber : myNumbers) {
                        if (!myNumber.trim().isEmpty() && !winningNumber.trim().isEmpty() &&
                                (Integer.parseInt(myNumber) == Integer.parseInt(winningNumber))) {
                            points = (points == 0) ? points = 1 : points * 2;
                            System.out.println("Winning number I own: " + myNumber + ", points: " + points);
                        }
                    }
                }
                System.out.println("points=" + points);
                sum += points;

                // read next line
                line = reader.readLine();
            }
            System.out.println("sum=" + sum);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}