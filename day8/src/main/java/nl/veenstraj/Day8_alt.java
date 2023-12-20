package nl.veenstraj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day8_alt {
    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day8/target/classes/input.txt"));
            String line = reader.readLine();

            char[] directions = new char[0];
            boolean leesDirections = true;
            int[] rvalues = new int[999999];
            int[] lvalues = new int[999999];
            int[] keys = new int[1000];
            int keyIndex = 0;
            while (line != null) {
                if (!line.trim().isEmpty()) {
                    if (leesDirections) {
                        directions = line.trim().toCharArray();
                        System.out.println("directions:" + new String(directions));
                        leesDirections = false;
                    } else {
                        String[] keyvalues = line.split("=");
                        char[] keyChar = keyvalues[0].trim().toCharArray();
                        int key = keyChar[0] * 100 * 100 + keyChar[1] * 100 + keyChar[2];
                        keys[keyIndex++] = key;
                        char[] valueLChar = keyvalues[1].trim().substring(1, 4).toCharArray();
                        char[] valueRChar = keyvalues[1].trim().substring(6, 9).toCharArray();
                        lvalues[key] = valueLChar[0] * 100 * 100 + valueLChar[1] * 100 + valueLChar[2];
                        rvalues[key] = valueRChar[0] * 100 * 100 + valueRChar[1] * 100 + valueRChar[2];

                        //System.out.println("Code=" + keyvalues[0] + " = " + key);
                    }
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();

            // opgave2
            int directionpos = 0;
            boolean endReached;
            long steps = 0;
            int[] inNodes = Arrays.stream(keys).filter(k -> ((k) % 100) == 'A').toArray();
            do {
                endReached = true;
                for (int nodeIndex = 0; nodeIndex < inNodes.length; nodeIndex++) {
                    inNodes[nodeIndex] = directions[directionpos] == 'R' ? rvalues[inNodes[nodeIndex]] : lvalues[inNodes[nodeIndex]];
                    if ((inNodes[nodeIndex] % 100) != 'Z') endReached = false;
                }
                if (++directionpos >= directions.length) directionpos = 0;
                if ((++steps % 4194304) == 0) System.out.printf("\r" + steps);

            } while (!endReached);
            System.out.println("steps:" + steps);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static String getNode(String value, char direction) {
        String result;
        if (direction == 'L') {
            result = value.trim().substring(1, 4);
        } else {
            result = value.trim().substring(6, 9);
        }
        return result;
    }
}
