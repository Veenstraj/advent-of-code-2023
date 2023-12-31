package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Day15 {
    private static final String inputfile = "./day15/target/classes/input.txt";

    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(inputfile));
            String line = reader.readLine();

            int sum = 0;
            int hash = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
                    String[] subject = line.trim().split(",");
                    for (String value : subject) {
                        hash = 0;
                        for (int i = 0; i < value.length(); i++) {
                            hash += (int) value.charAt(i);
                            hash = (hash * 17) % 256;
                        }
                        sum += hash;
                    }
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
