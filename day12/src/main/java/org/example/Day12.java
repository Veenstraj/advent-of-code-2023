package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;


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
            int[] groups;
            int sum = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
                    String conditionRecord = line.trim().split(" ")[0].trim();
                    String checksums = line.trim().split(" ")[1].trim();

                    // part 2: Extend with 5 copies of itself, separated by '?'
                    conditionRecord = conditionRecord + "?" + conditionRecord + "?" + conditionRecord + "?" + conditionRecord + "?" + conditionRecord;
                    checksums = checksums + "," + checksums + "," + checksums + "," + checksums + "," + checksums;

                    groups = Arrays.stream(checksums.split(",")).mapToInt(Integer::parseInt).toArray();
                    System.out.println("check " + conditionRecord);
                    String condRecordToCheck = conditionRecord.replaceAll("\\?", "d");
                    char[] arrayRecordTocheck = condRecordToCheck.toCharArray();
                    int sequence = 0;
                    do {
                        arrayRecordTocheck = getConditionrecord(arrayRecordTocheck, 0);
                        System.out.print("\rCheck " + String.valueOf(arrayRecordTocheck));//.replaceAll("d",".").replaceAll("h", "#"));
                        //System.out.println("\r" + sequence++);
                        if (checkGroups(arrayRecordTocheck, groups)) {
                            sum++;
                            //System.out.println("sum=" + sum);
                        }
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

                }
                if (rec[pos] == 'h') {
                    rec[pos] = 'd';
                }

            }
            return getConditionrecord(rec, pos + 1);
        }
        return rec;
    }

    private static boolean checkGroups(char[] arrayTocheck, int[] groups) {
        int group = 0;
        int[] foundGroups = new int[100];
        int groupindex = 0;
        for (char c : arrayTocheck) {
            if (c == '#' || c == 'h') {
                group++;
            } else {
                if (group > 0) {
                    if (groupindex >= groups.length || group != groups[groupindex]) {
                        return false;
                    }
                    foundGroups[groupindex++] = group;
                }
                group = 0;
            }
        }
        if (group > 0) {
            foundGroups[groupindex++] = group;
        }
        if (groupindex != groups.length) {
            return false;
        }
        return IntStream.range(0, groupindex).noneMatch(p -> foundGroups[p] != groups[p]);
    }
}
