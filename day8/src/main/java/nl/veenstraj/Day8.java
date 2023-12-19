package nl.veenstraj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            List<String> inNodes = nodes.keySet().stream().filter(k -> k.endsWith("A")).toList() ;
            List<String> nextNodes;
            //String tocheck = "";
            do {
              //  String check = "";
                nextNodes = new ArrayList<>();
                for (String inNode : inNodes) {
                    nextNodes.add(getNode(nodes.get(inNode), directions[directionpos]));
                //    check=check + inNode;

                   // System.out.println("node=" + inNode + ", step:" + steps);
                }
//                check = check + (String.valueOf(directions[directionpos]));
//                if (check.equals(tocheck)) {
//                    System.out.println("MATCH:" + check);
//                }
//                if ((steps % 1000000)==0) {
//                    tocheck = check;
//                    System.out.println("check = " + check);
//                }
                inNodes = nextNodes;
                if (++directionpos >= directions.length) {
                    //System.out.println("directionpos reset " + directionpos);
                    directionpos = 0;
                }
                //System.out.println("Ronde gedaan, volgende richting " + directions[directionpos]);
                if ((++steps % 1000000) ==0) System.out.printf("\r" + steps);
            } while (!nextNodes.stream().allMatch(n -> n.endsWith("Z")));
            System.out.println("step:" + steps);

            reader.close();
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
