package nl.veenstraj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day8 {
    public static void main(String[] args) {

        opgave1(); // en 2
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day8/target/classes/input.txt"));
            String line = reader.readLine();
            HashMap<String, String> nodes = new HashMap<>();
            char[] directions = new char[0];
            boolean leesDirections = true;
            while (line != null) {
                if (!line.trim().isEmpty()) {
                    if (leesDirections) {
                        directions = line.trim().toCharArray();
                        System.out.println("directions:" + new String(directions));
                        leesDirections = false;
                    } else {
                        String[] keyvalues = line.split("=");
                        nodes.put(keyvalues[0].trim(), keyvalues[1].trim());
                    }
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();

            // opgave1
            int directionpos = 0;
            long steps = 0;
//            String node = "AAA";
//            do {
//                node = getNode(nodes, node, directions[directionpos++]);
//                steps++;
//                if (directionpos >= directions.length) directionpos = 0;
//                System.out.println("node=" + node + ", step:" + steps);
//            } while (!node.equals("ZZZ"));

            // opgave2
            directionpos = 0;
            steps = 0;
            List<String> inNodes = nodes.keySet().stream().filter(k -> k.endsWith("A")).toList();
            List<String> nextNodes;
            do {
                nextNodes = new ArrayList<>();
                for (String inNode : inNodes) {
                    nextNodes.add(getNode(nodes.get(inNode), directions[directionpos]));
                }
                inNodes = nextNodes;
                if (++directionpos >= directions.length) directionpos = 0;
                if ((++steps % 4000000) == 0) System.out.printf("\r" + steps);
            } while (!nextNodes.stream().allMatch(n -> n.endsWith("Z")));
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
