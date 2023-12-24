package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
            int distance = 0;
            calcDistance(x, y, x, y, distance, ' ');
            System.out.println("maxDistance=" + _maxDistance);

            System.out.println("AantalInnerTiles= " + determineInnerTiles());

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static int calcDistance(int x, int y, int prevx, int prevy, int distance, char richting) {
        // System.out.println(("(" + (y + 1) + "," + (x + 1) + ") " + richting + " " + _map[y][x] + " : " + distance));
        char a = _map[y][x];
        _visited[y][x] = a == 'S' ? 'S' : (char) (distance % 10 + '0');
        if (a != '|' && a != 'L' && a != 'F' && (x - 1 != prevx)) { // going west
            switch (_map[y][x - 1]) {
                case '-', 'L', 'F' -> distance = calcDistance(x - 1, y, x, y, distance + 1, 'w');
                case 'S' -> distance = bepaalMax(distance);
            }
        }
        if (a != '|' && a != 'J' && a != '7' && (x + 1 != prevx)) { // going east
            switch (_map[y][x + 1]) {
                case '-', 'J', '7' -> distance = calcDistance(x + 1, y, x, y, distance + 1, 'e');
                case 'S' -> distance = bepaalMax(distance);
            }
        }
        if (a != '-' && a != '7' && a != 'F' && (y - 1 != prevy)) { // going north
            switch (_map[y - 1][x]) {
                case '|', 'F', '7' -> distance = calcDistance(x, y - 1, x, y, distance + 1, 'n');
                case 'S' -> distance = bepaalMax(distance);
            }
        }
        if (a != '-' && a != 'L' && a != 'J' && (y + 1 != prevy)) { // going south
            switch (_map[y + 1][x]) {
                case '|', 'J', 'L' -> distance = calcDistance(x, y + 1, x, y, distance + 1, 's');
                case 'S' -> distance = bepaalMax(distance);
            }
        }
        return distance;
    }


    private static int determineInnerTiles() {
        int nrOfInnerTiles = 0;
        System.out.println();
        for (int y = 0; y < _visited.length; y++) {
            for (int x = 0; x < _visited[y].length; x++) {
                _visited[y][x] = getTileType(x, y, x, y);
                if (_visited[y][x] == 'I') nrOfInnerTiles++;
                System.out.printf(String.valueOf(_visited[y][x]));
            }
            System.out.println();
        }
        return nrOfInnerTiles;
    }

    private static char getTileType(int x, int y, int prevx, int prevy) {
        //System.out.printf(" get (" + (y+1) + "," + (x+1)+ "):");
        if (_visited[y][x] == 0) {
            // aan de rand is geen inner tile
            if (x == 0 || x >= _visited[y].length - 1 || y == 0 || y >= _visited.length - 1) {
                return 'O';
            }
            if (x - 1 != prevx) {
                char a = getTileType(x - 1, y, x, y);
                if (a == 'O' || a == 'I') {
                    _visited[y][x] = a;
                    return a;
                }
            }
            if (x + 1 != prevx) {
                char a = getTileType(x + 1, y, x, y);
                if (a == 'O' || a == 'I') {
                    _visited[y][x] = a;
                    return a;
                }
            }
            if (y - 1 != prevy) {
                char a = getTileType(x, y - 1, x, y);
                if (a == 'O' || a == 'I') {
                    _visited[y][x] = a;
                    return a;
                }
            }
            if (y + 1 != prevy) {
                char a = getTileType(x, y + 1, x, y);
                if (a == 'O' || a == 'I') {
                    _visited[y][x] = a;
                    return a;
                }
            }
            return 'I';
        }
        return _visited[y][x];
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
