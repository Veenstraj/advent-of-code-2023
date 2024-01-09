package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* !! Zet VM Options op -Xss8M anders krijg je een stack overflow */
public class Day10 {

    private static final char[][] _map = new char[140][140];
    private static char[][] _visited;
    private static int _maxDistance = -1;

    public static void main(String[] args) {

        opgave1();

    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day10/target/classes/input.txt"));
            String line = reader.readLine();

            int index = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
                    _map[index++] = line.trim().toCharArray();
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
            _visited = new char[index][_map[0].length];

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
            calcDistance(x, y, x, y, 0, ' ');
            System.out.println("maxDistance=" + _maxDistance);
            determineInnerTiles();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static int calcDistance(int x, int y, int prevx, int prevy, int distance, char direction) {
        //System.out.println(("(" + (y + 1) + "," + (x + 1) + ") " + direction + " " + _map[y][x] + " : " + distance));
        char a = _map[y][x];
        _visited[y][x] = a; // == 'S' ? 'S' : (char) (distance % 10 + '0');
        if (a != '|' && a != 'L' && a != 'F' && (x - 1 != prevx) && (x - 1 >= 0)) { // going west
            switch (_map[y][x - 1]) {
                case '-' -> {
                    set(x - 1, y - 1, 'I');
                    set(x - 1, y + 1, 'O');
                    distance = calcDistance(x - 1, y, x, y, distance + 1, 'w');
                }
                case 'L' -> {
                    set(x - 2, y, 'O');
                    set(x - 1, y + 1, 'O');
                    distance = calcDistance(x - 1, y, x, y, distance + 1, 'w');
                }
                case 'F' -> {
                    set(x - 1, y - 1, 'I');
                    set(x - 2, y, 'I');
                    distance = calcDistance(x - 1, y, x, y, distance + 1, 'w');
                }
                case 'S' -> distance = bepaalMax(distance);
            }
        }
        if (a != '|' && a != 'J' && a != '7' && (x + 1 != prevx) && (x + 1 < _map[y].length)) { // going east
            switch (_map[y][x + 1]) {
                case '-' -> {
                    set(x + 1, y - 1, 'O');
                    set(x + 1, y + 1, 'I');
                    distance = calcDistance(x + 1, y, x, y, distance + 1, 'e');
                }
                case 'J' -> {
                    set(x + 1, y + 1, 'I');
                    set(x + 2, y, 'I');
                    distance = calcDistance(x + 1, y, x, y, distance + 1, 'e');
                }
                case '7' -> {
                    set(x + 1, y - 1, 'O');
                    set(x + 2, y, 'O');
                    distance = calcDistance(x + 1, y, x, y, distance + 1, 'e');
                }
                case 'S' -> distance = bepaalMax(distance);
            }
        }
        if (a != '-' && a != '7' && a != 'F' && (y - 1 != prevy) && (y - 1 >= 0)) { // going north
            switch (_map[y - 1][x]) {
                case '|' -> {
                    set(x - 1, y - 1, 'O');
                    set(x + 1, y - 1, 'I');
                    distance = calcDistance(x, y - 1, x, y, distance + 1, 'n');
                }
                case 'F' -> {
                    set(x - 1, y - 1, 'O');
                    set(x, y - 2, 'O');
                    distance = calcDistance(x, y - 1, x, y, distance + 1, 'n');
                }
                case '7' -> {
                    set(x + 1, y - 1, 'I');
                    set(x, y - 2, 'I');
                    distance = calcDistance(x, y - 1, x, y, distance + 1, 'n');
                }
                case 'S' -> distance = bepaalMax(distance);
            }
        }
        if (a != '-' && a != 'L' && a != 'J' && (y + 1 != prevy) && (y + 1 < _map.length)) { // going south
            switch (_map[y + 1][x]) {
                case '|' -> {
                    set(x - 1, y + 1, 'I');
                    set(x + 1, y + 1, 'O');
                    distance = calcDistance(x, y + 1, x, y, distance + 1, 's');
                }
                case 'J' -> {
                    set(x, y + 2, 'O');
                    set(x + 1, y + 1, 'O');
                    distance = calcDistance(x, y + 1, x, y, distance + 1, 's');
                }
                case 'L' -> {
                    set(x, y + 2, 'I');
                    set(x - 1, y + 1, 'I');
                    distance = calcDistance(x, y + 1, x, y, distance + 1, 's');
                }
                case 'S' -> distance = bepaalMax(distance);
            }
        }
        return distance;
    }

    private static void set(int x, int y, char value) {
        if (!isBorder(x, y) && (_visited[y][x] == 0 || _visited[y][x] == 'I' || _visited[y][x] == 'O'))
            _visited[y][x] = value;
    }

    private static void determineInnerTiles() {
        int nrOfInnerTiles = 0;
        int nrOfOuterTiles = 0;
        System.out.println();
        for (int y = 0; y < _visited.length; y++) {
            for (int x = 0; x < _visited[y].length; x++) {
                _visited[y][x] = getTileType(x, y, x, y);
                if (_visited[y][x] == 'I') nrOfInnerTiles++;
                if (_visited[y][x] == 'O') nrOfOuterTiles++;
                System.out.printf(String.valueOf(_visited[y][x]));
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Inner tiles: " + nrOfInnerTiles);
        System.out.println("Outer tiles: " + nrOfOuterTiles);
    }

    private static char getTileType(int x, int y, int prevx, int prevy) {
        //System.out.printf(" (" + (y+1) + "," + (x+1)+ "):");
        char v = _visited[y][x];
        if (v == 0) {
            // at the border there is no inner tile
            if (isBorder(x, y)) return 'O';
            if (x - 1 != prevx) {
                char a = getTileType(x - 1, y, x, y);
                if (a == 'O' || a == 'I') return a;
            }
            if (x + 1 != prevx) {
                char a = getTileType(x + 1, y, x, y);
                if (a == 'O' || a == 'I') return a;
            }
            if (y - 1 != prevy) {
                char a = getTileType(x, y - 1, x, y);
                if (a == 'O' || a == 'I') return a;
            }
            if (y + 1 != prevy) {
                char a = getTileType(x, y + 1, x, y);
                if (a == 'O' || a == 'I') return a;
            }
            return 'I';
        }
        return _visited[y][x];
    }

    private static boolean isBorder(int x, int y) {
        return x <= 0 || y <= 0 || y >= _visited.length - 1 || x >= _visited[y].length - 1;
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
