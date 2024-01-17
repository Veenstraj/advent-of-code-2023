package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day19 {
    private static final String inputfile = "./day19/target/classes/input.txt";
    private static Map<String, Workflow> _workflows = new HashMap<>();
    private static List<Rating> _ratings = new LinkedList<>();

    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(inputfile));
            String line = reader.readLine();

            long sum = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
                    processLine(line);
                }

                // read next line
                line = reader.readLine();
            }
            reader.close();
            sum = doorloop();

            System.out.println("sum=" + sum);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void processLine(String line) {
        if (!line.startsWith("{")) {
            readWorkflows(line);
        } else {
            readRatings(line);
        }
    }

    private static void readWorkflows(String line) {
        int order = 0;
        String wfName = line.substring(0, line.indexOf("{"));
        List<Rule> rules = new LinkedList<>();
        String[] rulesArray = line.substring(line.indexOf("{") + 1, line.indexOf("}")).split(",");
        for (String ruleIn : rulesArray) {
            Rule rule = null;
            if (ruleIn.length() < 4) {
                rule = new Rule(order++, 'Z', true, 0, ruleIn);
            } else {
                //         //System.out.println(ruleIn.substring(2, ruleIn.indexOf(":")));
                rule = new Rule(order++,
                        ruleIn.charAt(0),
                        ruleIn.charAt(1) != '<',
                        Integer.parseInt(ruleIn.substring(2, ruleIn.indexOf(":"))),
                        ruleIn.substring(ruleIn.indexOf(':') + 1));
            }
            rules.add(rule);
        }
        Workflow workflow = new Workflow(wfName, rules);
        _workflows.put(wfName, workflow);

        //System.out.printf("wfname=%s%n", workflow.name());
        for (Rule rule : workflow.rules()) {
            //System.out.printf("   %s%n", rule);
        }
    }

    private static void readRatings(String line) {
        String[] ratings = line.substring(1, line.indexOf('}')).split(",");
        int x = Integer.parseInt(ratings[0].substring(2));
        int m = Integer.parseInt(ratings[1].substring(2));
        int a = Integer.parseInt(ratings[2].substring(2));
        int s = Integer.parseInt(ratings[3].substring(2));
        Rating rating = new Rating(x, m, a, s);
        //System.out.println(rating);
        _ratings.add(rating);
    }

    private static long doorloop() {
        long count = 0;
        String wfNameNext = "in";
        for (Rating rating : _ratings) {
            //System.out.printf( wfNameNext + "->");
            Workflow wf = _workflows.get(wfNameNext);
            int index = 0;
            do {
                Rule rule = wf.rules().get(index++);
                //System.out.printf("Apply %s to %s", rule.toString(), rating.toString());
                switch (wfNameNext = rule.apply(rating)) {
                    case "A" -> {
                        //System.out.println(" result=A");
                        wfNameNext = "in";
                        count += rating.sum();
                        index = -1;
                    }
                    case "R" -> {
                        //System.out.println(" result=R");
                        wfNameNext = "in";
                        index = -1;
                    }
                    case "X" -> {
                        //System.out.println(" no match, next rule");
                        // condition was false, apply next rule
                    }
                    default -> {
                        wf = _workflows.get(wfNameNext);
                        index = 0;
                        //System.out.println(" rule=true, new=" + wfNameNext);
                    }
                }
            } while (index >= 0);
        }
        //System.out.println();
        return count;
    }

}
