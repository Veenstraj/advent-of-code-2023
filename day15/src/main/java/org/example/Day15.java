package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Day15 {
    private static final String inputfile = "./day15/target/classes/input.txt";

    private static final String[][] _box = new String[256][1000];

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
                    String[] subject = line.trim().split(",");
                    for (String value : subject) {
                        String label = value.split("[=-]")[0];
                        String operation = value.substring(label.length(), label.length() + 1);
                        String focalLength = "";
                        if (operation.equals("=")) {
                            focalLength = value.substring(label.length() + 1);
                        }
                        int boxnr = 0;
                        for (int i = 0; i < label.length(); i++) {
                            boxnr = hash(boxnr, label.charAt(i));
                        }
                        if (operation.equals("=")) {
                            storeInBox(boxnr, label, focalLength);
                        }
                        if (operation.equals("-")) {
                            removeFromBox(boxnr, label);
                        }
                        //printBoxes(value);
                    }
                    sum = calcSum();
                }

                // read next line
                line = reader.readLine();
            }
            reader.close();


            System.out.println("\nsum=" + sum);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static int hash(int hash, char input) {
        hash += input;
        return (hash * 17) % 256;
    }

    private static void storeInBox(int boxnr, String label, String focalLength) {
        int x = 0;
        for (; _box[boxnr][x] != null; x++) {
            if (_box[boxnr][x].startsWith(label)) {
                break;
            }
        }
        _box[boxnr][x] = label + " " + focalLength;
    }

    private static void removeFromBox(int boxnr, String label) {
        for (int x = 0; _box[boxnr][x] != null; x++) {
            if (_box[boxnr][x].startsWith(label)) {
                int x1 = x;
                for (; _box[boxnr][x1 + 1] != null; x1++) {
                    _box[boxnr][x1] = _box[boxnr][x1 + 1];
                }
                _box[boxnr][x1] = null;
                break;
            }
        }
    }

    private static int calcSum() {
        int sum = 0;
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 1000; j++) {
                if (_box[i][j] != null) {
                    int focalpos = _box[i][j].indexOf(" ");

                    System.out.println("sum+=" + (i + 1) + "*" + (j + 1) + "*" + Integer.parseInt(_box[i][j].substring(focalpos + 1)));
                    sum += (i + 1) * (j + 1) * Integer.parseInt(_box[i][j].substring(focalpos + 1));
                }
            }
        }
        return sum;
    }

    private static void printBoxes(String value) {
        System.out.println("\n\nAfter " + value + ":");
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 1000; j++) {
                if (_box[i][j] != null) {
                    if (j == 0) System.out.print("\nBox " + i + ":");
                    System.out.printf(" [" + _box[i][j] + "]");
                }
            }
        }

    }
}
