package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day16 {
    private static final int _maxDepth = 5000;
    private static final char[][] _map = new char[140][140];
    private static int _sizex;
    private static int _sizey;
    private static char[][] _visited;

    public static void main(String[] args) {

        opgave1();

    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./day16/target/classes/input.txt"));
            String line = reader.readLine();

            _sizey = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
                    _map[_sizey++] = line.trim().toCharArray();
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();

            _sizex = _map[0].length;
            _visited = new char[_sizey][_sizex];

            // starting point is (0,0) going east
            findTheWay(0, 0, 'e', 0);
//            printMap("Paths");
//            printContraption("Energization");
            printEnergized();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void findTheWay(int x, int y, char direction, int depth) {
//        printCoord(x, y);
//        System.out.printf("%c", direction);
        if (isOutside(x, y)) {
//            System.out.println("outside");
            return;
        }
//        System.out.printf("%c ", getMap(x, y));
        if (checkSameWay(x, y, direction, depth)) {
//            System.out.println("Same way...");
            return;
        }
        if (depth++ > _maxDepth) {
            System.out.println("Max depth reached.");
            return;
        }
        setVst(x, y, direction);
        switch (getMap(x, y)) {
            case '.', 'v', '^', '<', '>' -> {
                switch (direction) {
                    case 'w' -> findTheWay(x - 1, y, direction, depth);
                    case 'e' -> findTheWay(x + 1, y, direction, depth);
                    case 'n' -> findTheWay(x, y - 1, direction, depth);
                    case 's' -> findTheWay(x, y + 1, direction, depth);
                }
            }
            case '\\' -> {
                switch (direction) {
                    case 'w' -> findTheWay(x, y - 1, 'n', depth);
                    case 'e' -> findTheWay(x, y + 1, 's', depth);
                    case 'n' -> findTheWay(x - 1, y, 'w', depth);
                    case 's' -> findTheWay(x + 1, y, 'e', depth);
                }
            }
            case '/' -> {
                switch (direction) {
                    case 'w' -> findTheWay(x, y + 1, 's', depth);
                    case 'e' -> findTheWay(x, y - 1, 'n', depth);
                    case 'n' -> findTheWay(x + 1, y, 'e', depth);
                    case 's' -> findTheWay(x - 1, y, 'w', depth);
                }
            }
            case '|' -> {
                switch (direction) {
                    case 'w', 'e' -> {
                        if (!isOutside(x, y - 1) && getVst(x, y - 1) != 'n') findTheWay(x, y - 1, 'n', depth);
                        if (!isOutside(x, y + 1) && getVst(x, y + 1) != 's') findTheWay(x, y + 1, 's', depth);
                    }
                    case 'n' -> findTheWay(x, y - 1, direction, depth);
                    case 's' -> findTheWay(x, y + 1, direction, depth);
                }
            }
            case '-' -> {
                switch (direction) {
                    case 'w' -> findTheWay(x - 1, y, direction, depth);
                    case 'e' -> findTheWay(x + 1, y, direction, depth);
                    case 'n', 's' -> {
                        if (!isOutside(x - 1, y) && getVst(x - 1, y) != 'w') findTheWay(x - 1, y, 'w', depth);
                        if (!isOutside(x + 1, y) && getVst(x + 1, y) != 'e') findTheWay(x + 1, y, 'e', depth);
                    }
                }
            }
        }
    }

    // check if the beam goes the same way as a previous one
    private static boolean checkSameWay(int x, int y, char direction, int depth) {
        if (isOutside(x, y)) return false;

//        if ((depth > _maxDepth / 2) && getVst(x, y) == '#') {
//            return true;
//        }
        switch (getMap(x, y)) {
            case '.' -> {
                switch (direction) {
                    case 'w' -> setMap(x, y, '<');
                    case 'e' -> setMap(x, y, '>');
                    case 's' -> setMap(x, y, 'v');
                    case 'n' -> setMap(x, y, '^');
                }
            }
            case '<' -> {
                if (direction == 'w') {
                    return true;
                }
            }
            case '>' -> {
                if (direction == 'e') {
                    return true;
                }
            }
            case '^' -> {
                if (direction == 'n') {
                    return true;
                }
            }
            case 'v' -> {
                if (direction == 's') {
                    return true;
                }
            }
        }
        return false;
    }

    private static void setVst(int x, int y, char value) {
        if (!isOutside(x, y)) _visited[y][x] = value;
    }

    private static char getVst(int x, int y) {
        return _visited[y][x];
    }


    private static boolean isOutside(int x, int y) {
        return x < 0 || y < 0 || y >= _sizey || x >= _sizex;
    }

    private static char getMap(int x, int y) {
        return _map[y][x];
    }

    private static void setMap(int x, int y, char value) {
        if (!isOutside(x, y)) {
//            System.out.printf("Write %c%n", value);
            _map[y][x] = value;
        }
    }

    private static void print(int x, int y) {
        System.out.print(String.valueOf(getMap(x, y)) + " ");
    }

    private static void printCoord(int x, int y) {
        System.out.print("(" + (y + 1) + "," + (x + 1) + ")");
    }

    private static void printEnergized() {
        int energized = 0;
        for (int y = 0; y < _sizey; y++) {
            for (int x = 0; x < _sizex; x++) {
                if (getVst(x, y) != 0) energized++;
            }
        }
        System.out.println("Energized: " + energized);
    }

    private static void printContraption(String caption) {
        System.out.println(caption);
        for (int y = 0; y < _sizey; y++) {
            for (int x = 0; x < _sizex; x++) {
                System.out.print(String.valueOf(getVst(x, y)));
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void printMap(String caption) {
        System.out.println(caption);
        for (int y = 0; y < _sizey; y++) {
            for (int x = 0; x < _sizex; x++) {
                System.out.print(String.valueOf(getMap(x, y)));
            }
            System.out.println();
        }
        System.out.println();
    }

}
