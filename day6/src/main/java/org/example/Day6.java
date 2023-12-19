package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("./day6/target/classes/input2.txt"));
            String line = reader.readLine();

            List<Long> times = new ArrayList<>();
            List<Long> distances = new ArrayList<>();

            long sum = 1;
            while (line != null) {

                if (line.trim().startsWith("Time:")) {
                    times = Arrays.stream(line.substring(10).split(" "))
                            .filter(t -> !t.trim().isEmpty())
                            .map(t -> Long.parseLong(t.trim()))
                            .toList();
                }

                if (line.trim().startsWith("Distance:")) {
                    distances = Arrays.stream(line.substring(10).split(" "))
                            .filter(t -> !t.trim().isEmpty())
                            .map(t -> Long.parseLong(t.trim()))
                            .toList();
                }

                // read next line
                line = reader.readLine();
            }
            for (int race = 0; race < times.size(); race++) {
                long wins = 0;
                for (long timeButtonPress = 1; timeButtonPress < times.get(race); timeButtonPress++) {
                    long timeMove = times.get(race) - timeButtonPress;
                    long speed = timeButtonPress;
                    long distance = timeMove * speed;
                    //System.out.println("time button press:" + timeButtonPress + ", time move:" + timeMove + ", speed:" + speed + ", distance: " + distance);
                    if (distance > distances.get(race)) {
                        //System.out.println("Won the race: " + distance + " / " + distances.get(race));
                        wins++;
                    }

                }
                sum = sum * wins;
            }
            System.out.println("sum=" + sum);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}