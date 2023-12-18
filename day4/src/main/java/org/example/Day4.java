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
            reader = new BufferedReader(new FileReader("./day3/target/classes/input.txt"));
            String line = reader.readLine();

            int sum = 0;
            while (line != null) {

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