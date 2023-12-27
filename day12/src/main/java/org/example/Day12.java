package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Day12 {
    private static final String inputfile = "./day12/target/classes/input.txt";

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
                    String conditionRecord = line.trim().split(" ")[0].trim();
                    String checksums = line.trim().split(" ")[1].trim();

                    System.out.println("check " + conditionRecord);
                    String condRecordToCheck = conditionRecord.replaceAll("\\?", "d");
                    char[] arrayRecordTocheck = condRecordToCheck.toCharArray();
                    do {
                        arrayRecordTocheck = getConditionrecord(arrayRecordTocheck, 0);
                        //System.out.print("Check " + String.valueOf(arrayRecordTocheck).replaceAll("d",".").replaceAll("h", "#"));
                        if (checkGroups(arrayRecordTocheck, checksums)) sum++;
                    } while (!condRecordToCheck.equals(String.valueOf(arrayRecordTocheck)));
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();

            System.out.println("Sum = " + sum);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static char[] getConditionrecord(char[] rec, int pos) {
        if (pos < rec.length) {

            if (rec[pos] != '.' && rec[pos] != '#') {
                if (rec[pos] == 'd') { // d=dot
                    rec[pos] = 'h'; // h=hash
                    return rec;

                } else {
                    if (rec[pos] == 'h') {
                        rec[pos] = 'd';
                    }
                }
            }
            return getConditionrecord(rec, pos + 1);
        }
        return rec;
    }

    private static boolean checkGroups(char[] arrayTocheck, String checksums) {
        int group = 0;
        StringBuilder foundGroups = new StringBuilder();
        for (char c : arrayTocheck) {
            if (c == '#' || c == 'h') {
                group++;
            } else {
                if (group > 0) {
                    if (foundGroups.isEmpty()) {
                        foundGroups.append(group);
                    } else {
                        foundGroups.append(",").append(group);
                    }
                }
                group = 0;
            }
        }
        if (group > 0) {
            if (foundGroups.isEmpty()) {
                foundGroups.append(group);
            } else {
                foundGroups.append(",").append(group);
            }
        }
        if (foundGroups.toString().equals(checksums)) {
            // System.out.println(" > Match!");
            return true;
        } else {
            // System.out.println(" " + foundGroups.toString() + "/" + checksums);
            return false;
        }
    }
}
