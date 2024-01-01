package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day16 {
    private static final int _maxDepth = 900;
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
            printContraption("Energization");
            printEnergized();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void findTheWay(int x, int y, char direction, int depth) {
        if (isOutside(x, y)) return;
        if (++depth > _maxDepth) return;
//        printCoord(x, y);
        set(x, y, '#');
        switch (get(x, y)) {
            case '.' -> {
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
                        findTheWay(x, y - 1, 'n', depth);
                        findTheWay(x, y + 1, 's', depth);
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
                        findTheWay(x - 1, y, 'w', depth);
                        findTheWay(x + 1, y, 'e', depth);
                    }
                }
            }
        }
    }

    private static void set(int x, int y, char value) {
        if (!isOutside(x, y)) _visited[y][x] = value;
    }


    private static boolean isOutside(int x, int y) {
        return x < 0 || y < 0 || y >= _sizey || x >= _sizex;
    }

    private static char get(int x, int y) {
        return _map[y][x];
    }

//        private static void set ( int x, int y, char a){
//            _map[y][x] = a;
//        }

    private static void print(int x, int y) {
        System.out.print(String.valueOf(get(x, y)) + " ");
    }

    private static void printCoord(int x, int y) {
        System.out.print("(" + (y + 1) + "," + (x + 1) + ") ");
    }

    private static void printEnergized() {
        int energized = 0;
        for (int y = 0; y < _sizey; y++) {
            for (int x = 0; x < _sizex; x++) {
                if (_visited[y][x] == '#') energized++;
            }
        }
        System.out.println("Energized: " + energized);
    }

    private static void printContraption(String caption) {
        System.out.println(caption);
        for (int y = 0; y < _sizey; y++) {
            for (int x = 0; x < _sizex; x++) {
                System.out.print(String.valueOf(_visited[y][x]) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
