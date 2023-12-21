package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day9 {
    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day9/target/classes/input.txt"));
            String line = reader.readLine();

            int sum = 0;
            int sump = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
                    String[] parts = line.split(" ");

                    int[] result = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();

                    int nextNumber = predictNumber(result);
                    sum += nextNumber;
                    System.out.println("Next=" + nextNumber + ", sum=" + sum);

                    // opgave 2

                    int prevNumber = predictPrevNumber(result);
                    sump += prevNumber;
                    System.out.println("Prev=" + prevNumber + ", sum=" + sump);
                }
                // read next line
                line = reader.readLine();
            }
            System.out.println("sum next=" + sum + ", sum prev=" + sump);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static int predictNumber(int[] input) {
        int[] differences = new int[input.length - 1];
        boolean allZeros = true;
        for (int i = 0; i < input.length - 1; i++) {
            System.out.printf(input[i] + " ");
            differences[i] = input[i + 1] - input[i];
            if (differences[i] != 0) allZeros = false;
        }
        if (allZeros) {
            System.out.println("nieuw:" + (input[input.length - 1]));
            return (input[input.length - 1]);
        }

        System.out.println();
        return input[input.length - 1] + predictNumber(differences);

    }

    private static int predictPrevNumber(int[] input) {
        int[] differences = new int[input.length - 1];
        boolean allZeros = true;
        for (int i = 0; i < input.length - 1; i++) {
            System.out.printf(input[i] + " ");
            differences[i] = input[i + 1] - input[i];
            if (differences[i] != 0) allZeros = false;
        }
        if (allZeros) {
            System.out.println("nieuw:" + (input[0]));
            return (input[0]);
        }

        System.out.println();
        return input[0] - predictPrevNumber(differences);

    }

}
