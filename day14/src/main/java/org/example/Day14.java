package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day14 {
    private static final String inputfile = "./day14/target/classes/input.txt";

    private static final char[][] _map = new char[140][140];
    private static int _sizex;
    private static int _sizey;

    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(inputfile));
            String line = reader.readLine();

            int sum = 0;
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

            tiltNorth();

            System.out.println("sum=" + calcSum());

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void tiltNorth() {
        for (int y = 0; y < _sizey; y++) {
            for (int x = 0; x < _sizex; x++) {
                moveRockNorth(x, y);
                print(x, y);
            }
            System.out.println();
        }
    }

    /* returns true when location contains rock (O) */
    private static boolean moveRockNorth(int x, int y) {
        if (x >= _sizex || y > _sizey) return false;
        switch (get(x, y)) {
            case '.':
                if (moveRockNorth(x, y + 1)) {
                    set(x, y + 1, '.');
                    set(x, y, 'O');
                    return true;
                }
                break;
            case '#':
                break;
            case 'O':
                return true;
        }
        return false;
    }

    private static long calcSum() {
        long sum = 0;
        for (int y = 0; y < _sizey; y++) {
            for (int x = 0; x < _sizex; x++) {
                int rank = _sizey - y;
                if (get(x, y) == 'O') {
                    sum += rank;
                }
            }
        }
        return sum;
    }

    private static char get(int x, int y) {
        return _map[y][x];
    }

    private static void set(int x, int y, char a) {
        _map[y][x] = a;
    }

    private static void print(int x, int y) {
        System.out.print(String.valueOf(get(x, y)));
    }
}
