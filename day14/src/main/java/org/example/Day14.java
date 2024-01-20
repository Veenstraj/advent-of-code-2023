package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day14 {
    private static final String inputfile = "./day14/target/classes/input.txt";
    private static final long MAX_CYCLES = 1000000000;
    private static final char[][] _map = new char[140][140];
    private static int _sizex;
    private static int _sizey;

    private static long _sumx = 0;
    private static long _sumy = 0;
    private static long _previ = 0;

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
            // printPlatform("orig");
            for (int i = 0; i < MAX_CYCLES; i++) {
                if (i % 100 == 0) System.out.printf("\r" + i);
                doCycle(i);

                // the solution is based on the existence of a repeating pattern
                long remainingcycles = checkDuplicate(i);
                if (remainingcycles >= 0) {
                    System.out.println();
                    for (int j = 0; j < remainingcycles; j++) {
                        System.out.print("\r > " + (i + j));
                        doCycle(i + j);
                    }
                    System.out.println();
                    break;
                }
            }

            System.out.println("sum=" + calcSum());

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void doCycle(long i) {
        tiltNorth();
        //printPlatform("north " + i);
        tiltWest();
        //printPlatform("west " + i);
        tiltSouth();
        //printPlatform("south " + i);
        tiltEast();
        //printPlatform("east " + i);
    }

    private static void tiltNorth() {
        for (int x = 0; x < _sizex; x++) {
            moveRockNorth(x, 0);
        }
    }

    private static void tiltWest() {
        for (int y = 0; y < _sizey; y++) {
            moveRockWest(0, y);
        }
    }

    private static void tiltSouth() {
        for (int x = 0; x < _sizex; x++) {
            moveRockSouth(x, _sizey - 1);
        }
    }

    private static void tiltEast() {
        for (int y = 0; y < _sizey; y++) {
            moveRockEast(_sizex - 1, y);
        }
    }

    /* returns true when location contains rock (O) */
    private static boolean moveRockNorth(int x, int y) {
        if (x >= _sizex || y > _sizey) return false;
        char a = get(x, y);
        boolean nextO = moveRockNorth(x, y + 1);
        if (a == 'O') {
            return true;
        }
        if (a == '.' && nextO) {
            set(x, y + 1, '.');
            set(x, y, 'O');
            moveRockNorth(x, y + 1); // re-check
            return true;
        }
        return false;
    }

    private static boolean moveRockWest(int x, int y) {
        if (x >= _sizex || y > _sizey) return false;
        char a = get(x, y);
        boolean nextO = moveRockWest(x + 1, y);
        if (a == 'O') {
            return true;
        }
        if (a == '.' && nextO) {
            set(x + 1, y, '.');
            set(x, y, 'O');
            moveRockWest(x + 1, y); // re-check
            return true;
        }
        return false;
    }

    private static boolean moveRockSouth(int x, int y) {
        if (x >= _sizex || x < 0 || y < 0 || y > _sizey) return false;
        char a = get(x, y);
        boolean nextO = moveRockSouth(x, y - 1);
        if (a == 'O') {
            return true;
        }
        if (a == '.' && nextO) {
            set(x, y - 1, '.');
            set(x, y, 'O');
            moveRockSouth(x, y - 1); // re-check
            return true;
        }
        return false;
    }

    private static boolean moveRockEast(int x, int y) {
        if (x >= _sizex || x < 0 || y < 0 || y > _sizey) return false;
        char a = get(x, y);
        boolean nextO = moveRockEast(x - 1, y);
        if (a == 'O') {
            return true;
        }
        if (a == '.' && nextO) {
            set(x - 1, y, '.');
            set(x, y, 'O');
            moveRockEast(x - 1, y); // re-check
            return true;
        }
        return false;
    }

    private static long checkDuplicate(long i) {
        long sumx = 0;
        long sumy = 0;

        for (int x = 0; x < _sizex; x++) {
            for (int y = 0; y < _sizey; y++) {
                int rank = _sizey - y;
                if (get(x, y) == 'O') {
                    sumy += rank;
                    sumx += x;
                }
            }
        }
        if (i == 100) { // only after 100 cycles so the repeating combinations are stable.
            _sumx = sumx;
            _sumy = sumy;
            _previ = i;
            //System.out.println("_sumx=" + _sumx + ", _sumy=" + _sumy);
            return -1;
        }
        if (_sumx == sumx && _sumy == sumy) {
            long delta = i - _previ;
            System.out.println("\rduplicate sumx=" + sumx + " sumy=" + sumy + " @i=" + i + " d=" + delta);

//            printPlatform("");
            long cycles = (MAX_CYCLES - i) / delta;
            cycles = MAX_CYCLES - (cycles * delta) - i - 1;
            _previ = i;
            System.out.println("Remaining cycles = " + cycles);
            return cycles;
        }
        return -1;
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
        System.out.print(get(x, y) + " ");
    }

    private static void printPlatform(String direction) {
        System.out.println(direction);
        for (int y = 0; y < _sizey; y++) {
            for (int x = 0; x < _sizex; x++) {
                print(x, y);
            }
            System.out.println();
        }
        System.out.println();
    }
}
