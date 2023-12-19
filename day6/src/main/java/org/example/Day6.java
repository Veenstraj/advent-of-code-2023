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
            reader = new BufferedReader(new FileReader("./day6/target/classes/input.txt"));
            String line = reader.readLine();

            List<Integer> times = new ArrayList<>();
            List<Integer> distances = new ArrayList<>();

            int sum = 1;
            while (line != null) {

                if (line.trim().startsWith("Time:")) {
                    times = Arrays.stream(line.substring(10).split(" "))
                            .filter(t -> !t.trim().isEmpty())
                            .map(t -> Integer.parseInt(t.trim()))
                            .toList();
                }

                if (line.trim().startsWith("Distance:")) {
                    distances = Arrays.stream(line.substring(10).split(" "))
                            .filter(t -> !t.trim().isEmpty())
                            .map(t -> Integer.parseInt(t.trim()))
                            .toList();
                }

                // read next line
                line = reader.readLine();
            }
            for (int race = 0; race < times.size(); race++) {
                int wins = 0;
                for (int timeButtonPress = 1; timeButtonPress < times.get(race); timeButtonPress++) {
                    int timeMove = times.get(race) - timeButtonPress;
                    int speed = timeButtonPress;
                    int distance = timeMove * speed;
                    System.out.println("time button press:" + timeButtonPress + ", time move:" + timeMove + ", speed:" + speed + ", distance: " + distance);
                    if (distance > distances.get(race)) {
                        System.out.println("Won the race: " + distance + " : " + distances.get(race));
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