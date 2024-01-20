package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day19 {
    private static final String inputfile = "./day19/target/classes/inputTst.txt";
    private static final int MAX_PER_RATING = 4000;
    private static final Map<String, Workflow> _workflows = new HashMap<>();
    private static final List<Rating> _ratings = new LinkedList<>();

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

            sum = doorloop1();
            System.out.println("1. sum=" + sum);

            sum = doorloop2();
            System.out.println("2. sum=" + sum);

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
                rule = new Rule(order++, "Z", true, 0, ruleIn);
            } else {
                //         //System.out.println(ruleIn.substring(2, ruleIn.indexOf(":")));
                rule = new Rule(order++,
                        ruleIn.substring(0, 1),
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

    private static long doorloop1() {
        long count = 0;
        String wfNameNext = "in";
        for (Rating rating : _ratings) {
            Workflow wf = _workflows.get(wfNameNext);
            int index = 0;
            do {
                System.out.printf(" %s->", wfNameNext);
                Rule rule = wf.rules().get(index++);
//                System.out.printf("Apply %s to %s", rule.toString(), rating.toString());
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

    private static long doorloop2() {
        String wfNameNext = "in";
        Rating rating = new Rating(MAX_PER_RATING, MAX_PER_RATING, MAX_PER_RATING, MAX_PER_RATING);

        Rating rating1 = getCount(wfNameNext, rating, 0);
        return (_totaalCount);
    }
//167409079868000
//163966348268000


    private static Rating getCount(String wfName, Rating rating, int depth) {
        Rating restRating = rating.clone();
        Workflow wf = _workflows.get(wfName);

        for (Rule rule : wf.rules()) {
            boolean modified = false;

            System.out.printf("%s%s->%s(%s)->%n", indent(depth), rating, wfName, rule);

            if (!rule.rating().equals("Z")) { // Z=er is geen conditie, ga rechtstreeks
                if (rule.greaterThan()) {
                    modified = rating.narrowDown(rule.rating(), rule.value() + 1, MAX_PER_RATING);
                    restRating.narrowDown(rule.rating(), 1, rule.value());
                } else {
                    modified = rating.narrowDown(rule.rating(), 1, rule.value() - 1);
                    restRating.narrowDown(rule.rating(), rule.value(), MAX_PER_RATING);
                }
            } else {
                modified = true;
            }
            if (modified) {
                rating = nextWorkflow(rule.destination(), rating.clone(), depth);
                if (rule.destination().equals("A")) {
                    _totaalCount += rating.product();

                    System.out.printf("%sResultaat %s(%s)%s =%d, totaal=%d%n", indent(depth + 1), wfName, rule, rating, rating.product(), _totaalCount);
                }
            } else {
                System.out.printf("%sGeen modificatie.. ga door met volgende rule%n", indent(depth));
            }
            rating = restRating;
        }
        return rating;
    }

    private static Rating nextWorkflow(String nextWorkFlow, Rating rating, int depth) {
        switch (nextWorkFlow) {
            case "A" -> {
            }
            case "R" -> rating.setAllZero();
            default -> {
                rating = getCount(nextWorkFlow, rating, ++depth);
            }
        }
        return rating;
    }

    private static String indent(int depth) {
        return "\t".repeat(Math.max(0, depth));
    }

}