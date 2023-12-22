package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day10 {

    private static final char[][] _map = new char[140][140];
    private static int _maxDistance = -1;

    public static void main(String[] args) {

        opgave1();

    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day10/target/classes/inputTest.txt"));
            String line = reader.readLine();

            int index = 0;
            int sum = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
                    _map[index++] = line.trim().toCharArray();
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();

            int x = -1;
            int y = -1;
            // find starting point (S)
            for (int mapy = 0; mapy < _map.length && x == -1; mapy++) {
                for (int mapx = 0; mapx < _map[mapy].length && x == -1; mapx++) {
                    if (_map[mapy][mapx] == 'S') {
                        x = mapx;
                        y = mapy;
                    }
                }
            }

            System.out.println("Starting at (" + (y + 1) + "," + (x + 1) + ")");
            int distance = 0;
            int maxDistance = calcDistance(x, y, x, y, distance, ' ');

            System.out.println("maxDistance=" + _maxDistance);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static int calcDistance(int x, int y, int prevx, int prevy, int distance, char richting) {
        System.out.println(("(" + (y + 1) + "," + (x + 1) + ") " + richting + " " + _map[y][x] + " : " + distance));
        char a = _map[y][x];
        boolean culdesac = true;
        if (a != '|' && a != 'L' && a != 'F' && x - 1 >= 0 && (x - 1 != prevx)) { // going west
            switch (_map[y][x - 1]) {
                case '-', 'L', 'F' -> {
                    distance = calcDistance(x - 1, y, x, y, distance + 1, 'w');
                    culdesac = false;
                }
                case 'S' -> { // back at start
                    distance = bepaalMax(distance);
                }
                default -> {
                }
            }
        }
        if (a != '|' && a != 'J' && a != '7' && x + 1 < _map[y].length && (x + 1 != prevx)) { // going east
            switch (_map[y][x + 1]) {
                case '-', 'J', '7' -> {
                    distance = calcDistance(x + 1, y, x, y, distance + 1, 'e');
                    culdesac = false;
                }
                case 'S' -> { // back at start
                    distance = bepaalMax(distance);
                }
                default -> {
                }
            }
        }
        if (a != '-' && a != '7' && a != 'F' && y - 1 >= 0 && (y - 1 != prevy)) { // going north
            switch (_map[y - 1][x]) {
                case '|', 'F', '7' -> {
                    distance = calcDistance(x, y - 1, x, y, distance + 1, 'n');
                    culdesac = false;
                }
                case 'S' -> { // back at start
                    distance = bepaalMax(distance);
                }
                default -> {
                }
            }
        }
        if (a != '-' && a != 'L' && a != 'J' && y + 1 < _map.length && (y + 1 != prevy)) { // going south
            switch (_map[y + 1][x]) {
                case '|', 'J', 'L' -> {
                    distance = calcDistance(x, y + 1, x, y, distance + 1, 's');
                    culdesac = false;
                }
                case 'S' -> { // back at start
                    distance = bepaalMax(distance);
                }
                default -> {
                }
            }
        }
        if (culdesac) {
            if (distance > _maxDistance) {
                _maxDistance = distance;
                System.out.println("cds: maxDistance=" + _maxDistance);
            }
            distance = 0;
            System.out.printf("<");
        }
        return distance;
    }

    private static int bepaalMax(int distance) {
        int tempDistance = ((distance % 2) == 0) ? distance / 2 : distance / 2 + 1;
        if (tempDistance > _maxDistance) {
            _maxDistance = tempDistance;
            System.out.println("maxDistance=" + _maxDistance);
        }
        return 0;
    }
}
