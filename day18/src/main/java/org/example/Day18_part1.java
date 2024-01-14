package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day18_part1 {
    private static final String inputfile = "./day18/target/classes/inputTst_jos.txt";

    private static final int CENTRE_X = 500;
    private static final int CENTRE_Y = 500;
    private static final char[][] _map = new char[2 * CENTRE_Y][2 * CENTRE_X];

    private static int _minx = CENTRE_X;
    private static int _maxx = 0;
    private static int _miny = CENTRE_Y;
    private static int _maxy = 0;

    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(inputfile));
            String line = reader.readLine();
            int x = 0;
            int y = 0;
            int sum = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
                    String richting = line.trim().split(" ")[0];
                    int nrOfMeters = Integer.valueOf(line.trim().split(" ")[1]);
                    String color = line.trim().split(" ")[2];
                    switch (richting) {
                        case "R" -> {
                            for (int i = 0; i < nrOfMeters; i++) {
                                setMap(y, x + i, '#');
                            }
                            x += nrOfMeters;
                        }
                        case "L" -> {
                            for (int i = 0; i < nrOfMeters; i++) {
                                setMap(y, x - i, '#');
                            }
                            x -= nrOfMeters;
                        }
                        case "U" -> {
                            for (int i = 0; i < nrOfMeters; i++) {
                                setMap(y - i, x, '#');
                            }
                            y -= nrOfMeters;
                        }
                        case "D" -> {
                            for (int i = 0; i < nrOfMeters; i++) {
                                setMap(y + i, x, '#');
                            }
                            y += nrOfMeters;
                        }
                    }
                    _maxx = Math.max(x + 2, _maxx);
                    _maxy = Math.max(y + 2, _maxy);
                    _minx = Math.min(x - 1, _minx);
                    _miny = Math.min(y - 1, _miny);
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
            digout();
            digout();
            sum = printMap();
            System.out.printf("minx=%d, miny=%d, maxx=%d, maxy=%d%n", _minx, _miny, _maxx, _maxy);

            System.out.println("sum=" + sum);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void digout() {
        setMap(_miny, _minx, '.'); // upper left corner is Outside
        for (int y = _miny; y < _maxy; y++) {
            for (int x = _minx; x < _maxx; x++) {
                setMapDot(y, x);
            }
        }
        setMap(_maxy - 1, _maxx - 1, '.'); // lower right corner is Outside
        for (int x = _maxx - 1; x >= _minx; x--) {
            for (int y = _maxy - 1; y >= _miny; y--) {
                setMapDot(y, x);
            }
        }
        setMap(_maxy - 1, _minx, '.'); // lower left corner is Outside
        for (int y = _maxy - 1; y >= _miny; y--) {
            for (int x = _minx; x < _maxx; x++) {
                setMapDot(y, x);
            }
        }
        setMap(_miny, _maxx - 1, '.'); // upper right corner is Outside
        for (int x = _maxx - 1; x >= _minx; x--) {
            for (int y = _miny; y < _maxy; y++) {
                setMapDot(y, x);
            }
        }

    }

    private static void setMapDot(int y, int x) {
        if (getMap(y, x) == '#') return;
        if (!isOutside(x - 1, y) && getMap(y, x - 1) == '.') setMap(y, x, '.');
        if (!isOutside(x + 1, y) && getMap(y, x + 1) == '.') setMap(y, x, '.');
        if (!isOutside(x, y - 1) && getMap(y - 1, x) == '.') setMap(y, x, '.');
        if (!isOutside(x, y + 1) && getMap(y + 1, x) == '.') setMap(y, x, '.');
    }

    private static boolean isOutside(int x, int y) {
        return x < _minx || y < _miny || y >= _maxy || x >= _maxx;
    }

    private static void setMap(int y, int x, char c) {
        _map[y + CENTRE_Y][x + CENTRE_X] = c;
    }

    private static char getMap(int y, int x) {
        return _map[y + CENTRE_Y][x + CENTRE_X];
    }

    private static int printMap() {
        int count = 0;
        int prevCount = 0;
        for (int y = _miny; y < _maxy; y++) {
            for (int x = _minx; x < _maxx; x++) {
                System.out.printf("%c", getMap(y, x));
                if (getMap(y, x) == '#' || getMap(y, x) == 0) count++;
            }
            System.out.printf("%d: delta=%d, innerBlocks=%d%n", y, count - prevCount, count);
            prevCount = count;
        }
        return count;
    }
}
