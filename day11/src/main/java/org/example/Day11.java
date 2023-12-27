package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day11 {
    private static final char[][] _map = new char[300][300];
    private static char[][] _visited;
    private static int _maxx;
    private static int _maxy;
    private static Map<Integer, Galaxy> _galaxies = new HashMap<>();


    public static void main(String[] args) {

        opgave1();

    }

    public static void opgave1() {
        BufferedReader reader;
        char[][] mapTemp = new char[300][300];

        try {
            reader = new BufferedReader(new FileReader("./day11/target/classes/input.txt"));
            String line = reader.readLine();

            int index = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
                    char[] thisLine = line.trim().toCharArray();
                    mapTemp[index++] = thisLine;
                    if (!hasGalaxies(thisLine)) mapTemp[index++] = thisLine;  // expand space row
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();

            // copy and expand
            copyAndExpand(mapTemp, index);
            printSpace();
            System.out.println("Nr of Galaxies=" + findGalaxies());

            long sumShortestPath = sumShortestPaths();
            System.out.println("Sum = " + sumShortestPath);

            //_visited = new char[index][_map[0].length];

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static boolean hasGalaxies(char[] line) {
        for (char a : line) if (a == '#') return true;
        return false;
    }

    private static void copyAndExpand(char[][] mapTemp, int maxy) {
        int newx = 0;
        for (int x = 0; x < mapTemp[0].length; x++, newx++) {
            boolean hasGalaxies = false;
            for (int y = 0; y < maxy; y++) {
                if (mapTemp[y][x] == '#') hasGalaxies = true;
                _map[y][newx] = mapTemp[y][x];
            }
            if (!hasGalaxies) {
                newx++;  // expand space column
                for (int y = 0; y < maxy; y++) {
                    _map[y][newx] = mapTemp[y][x];
                }
            }
        }
        _maxx = newx;
        _maxy = maxy;
    }

    private static int findGalaxies() {
        int nrOfGalaxy = 0;
        for (int y = 0; y < _maxy; y++) {
            for (int x = 0; x < _maxx; x++) {
                if (_map[y][x] == '#') {
                    _galaxies.put(nrOfGalaxy, new Galaxy(nrOfGalaxy, x, y));
                    nrOfGalaxy++;
                }
            }
        }
        return nrOfGalaxy;
    }

    private static long sumShortestPaths() {
        long sum = 0;
        int nrOfDistances = 0;
        for (int galaxynrSrc = 0; galaxynrSrc < _galaxies.keySet().size() - 1; galaxynrSrc++) {
            for (int galaxynrDest = galaxynrSrc + 1; galaxynrDest < _galaxies.keySet().size(); galaxynrDest++) {
                Galaxy galaxySrc = _galaxies.get(galaxynrSrc);
                Galaxy galaxyDest = _galaxies.get(galaxynrDest);
                long distance = calcShortestPath(galaxySrc.x, galaxySrc.y, galaxyDest.x, galaxyDest.y, 0);
                System.out.println(String.format("%02d: Galaxy %d (%d,%d) to %d (%d,%d) is %d", nrOfDistances++,
                        galaxySrc.number, galaxySrc.y + 1,
                        galaxySrc.x + 1, galaxyDest.number, galaxyDest.y + 1, galaxyDest.x + 1, distance));
                sum += distance;
            }
        }
        return sum;
    }

    private static long calcShortestPath(int x, int y, int destx, int desty, long distance) {
        return Math.abs(desty - y) + Math.abs(destx - x);
    }

    private static void printSpace() {
        for (int y = 0; y < _maxy; y++) {
            for (int x = 0; x < _maxx; x++) {
                System.out.printf(String.valueOf(_map[y][x]));
            }
            System.out.println();
        }
    }

    static class Galaxy {
        int number;
        int x;
        int y;

        public Galaxy(int number, int x, int y) {
            this.number = number;
            this.x = x;
            this.y = y;
        }
    }
}

