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
    private static final int MAX_PER_RATING = 4000;
    private static final Map<String, Workflow> _workflows = new HashMap<>();
    private static final List<Rating> _ratings = new LinkedList<>();

    public static void main(String[] args) {

        opgave();
    }

    public static void opgave() {
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

            sum = task1();
            System.out.println("1. sum=" + sum);

            sum = task2();
            System.out.println("\n2. sum=" + sum);

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
        String wfName = line.substring(0, line.indexOf("{"));
        List<Rule> rules = new LinkedList<>();
        String[] rulesArray = line.substring(line.indexOf("{") + 1, line.indexOf("}")).split(",");
        for (String ruleIn : rulesArray) {
            Rule rule = null;
            if (ruleIn.length() < 4) {
                rule = new Rule("Z", true, 0, ruleIn); // Z=Geen conditie, recht naar volgende workflow
            } else {
                //         //System.out.println(ruleIn.substring(2, ruleIn.indexOf(":")));
                rule = new Rule(ruleIn.substring(0, 1),
                        ruleIn.charAt(1) != '<',
                        Integer.parseInt(ruleIn.substring(2, ruleIn.indexOf(":"))),
                        ruleIn.substring(ruleIn.indexOf(':') + 1));
            }
            rules.add(rule);
        }
        Workflow workflow = new Workflow(wfName, rules);
        _workflows.put(wfName, workflow);
    }

    private static void readRatings(String line) {
        String[] ratings = line.substring(1, line.indexOf('}')).split(",");
        int x = Integer.parseInt(ratings[0].substring(2));
        int m = Integer.parseInt(ratings[1].substring(2));
        int a = Integer.parseInt(ratings[2].substring(2));
        int s = Integer.parseInt(ratings[3].substring(2));
        Rating rating = new Rating(x, m, a, s);
        _ratings.add(rating);
    }

    private static long task1() {
        long count = 0;
        String wfNameNext = "in";
        for (Rating rating : _ratings) {
            Workflow wf = _workflows.get(wfNameNext);
            int index = 0;
            do {
                System.out.printf(" %s->", wfNameNext);
                Rule rule = wf.rules().get(index++);
                switch (wfNameNext = rule.apply(rating)) {
                    case "A" -> {
                        System.out.println(" result=A");
                        wfNameNext = "in";
                        count += rating.sum();
                        index = -1;
                    }
                    case "R" -> {
                        System.out.println(" result=R");
                        wfNameNext = "in";
                        index = -1;
                    }
                    case "X" -> {
                        // condition was false, apply next rule
                    }
                    default -> {
                        wf = _workflows.get(wfNameNext);
                        index = 0;
                    }
                }
            } while (index >= 0);
        }
        System.out.println();
        return count;
    }

    private static long _totaalCount = 0;

    private static long task2() {
        String wfNameNext = "in";
        Rating rating = new Rating(MAX_PER_RATING, MAX_PER_RATING, MAX_PER_RATING, MAX_PER_RATING);

        getCount(wfNameNext, rating, 0);
        return (_totaalCount);
    }

    //167409079868000

    private static void getCount(String wfName, Rating rating, int depth) {
        Workflow wf = _workflows.get(wfName);

        for (Rule rule : wf.rules()) {
            Rating restRating = rating.clone();

            System.out.printf("%s%s->%s(%s)->%n", indent(depth), rating, wfName, rule);

            if (!rule.rating().equals("Z")) { // Z=er is geen conditie, ga rechtstreeks
                if (rule.greaterThan()) {
                    rating.narrowDown(rule.rating(), rule.value() + 1, MAX_PER_RATING);
                    restRating.narrowDown(rule.rating(), 1, rule.value());
                } else {
                    rating.narrowDown(rule.rating(), 1, rule.value() - 1);
                    restRating.narrowDown(rule.rating(), rule.value(), MAX_PER_RATING);
                }
            }
            switch (rule.destination()) {
                case "A" -> {
                    _totaalCount += rating.product();
                    System.out.printf("%sResultaat %s(%s) %s=%d, totaal=%d%n", indent(depth + 1), wfName, rule, rating, rating.product(), _totaalCount);
                }
                case "R" -> {
                }
                default -> {
                    getCount(rule.destination(), rating, depth + 1);
                    System.out.printf("%s<%n", indent(depth));
                }
            }
            rating = restRating; // input for the next rule of the workflow
        }
    }

    private static String indent(int depth) {
        return "\t".repeat(Math.max(0, depth));
    }

}