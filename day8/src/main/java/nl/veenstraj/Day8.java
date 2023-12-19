package nl.veenstraj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Day8 {
    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day8/target/classes/input.txt"));
            String line = reader.readLine();
            HashMap<String, String> nodes = new HashMap<>();
            char[] directions = new char[0];
            boolean leesDirections = true;
            int sum = 0;
            while (line != null) {
                if (!line.trim().isEmpty()) {
                    if (leesDirections) {
                        directions = line.trim().toCharArray();
                        leesDirections = false;
                    } else {
                        String[] keyvalues = line.split("=");
                        nodes.put(keyvalues[0].trim(), keyvalues[1].trim());
                    }
                }
                // read next line
                line = reader.readLine();
            }
            int directionpos = 0;
            long steps = 0;
            String node = "AAA";
            do {
                node = getNode(nodes, node, directions[directionpos++]);
                steps++;
                if (directionpos >= directions.length) directionpos = 0;
                System.out.println("node=" + node + ", step:" + steps);
            } while (!node.equals("ZZZ"));
            System.out.println("sum=" + sum);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static String getNode(HashMap<String, String> nodes, String node, char direction) {
        String value = nodes.get(node);
        if (direction == 'L') return value.trim().substring(1, 4);
        return value.trim().substring(6, 9);
    }
}
