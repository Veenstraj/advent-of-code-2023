package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day11 {
    private static final String inputfile = "./day11/target/classes/input.txt";
    private static final int _expansion = 999999;
    private static final char[][] _map = new char[150][150];
    private static final int[] _expandedY = new int[15];
    private static final int[] _expandedX = new int[15];
    private static int _maxx;
    private static int _maxy;
    private static final Map<Integer, Galaxy> _galaxies = new HashMap<>();


    public static void main(String[] args) {

        opgave1();

    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(inputfile));
            String line = reader.readLine();

            int index = 0;
            int expansion = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
                    char[] thisLine = line.trim().toCharArray();
                    if (!hasGalaxies(thisLine)) _expandedY[expansion++] = index;
                    _map[index++] = thisLine;
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();

            _maxy = index;
            _maxx = _map[0].length;
            lookForExpansionX();
            System.out.println("maxx=" + _maxx + ", maxy=" + _maxy);
            System.out.println("Nr of Galaxies=" + findGalaxies());

            long sumShortestPath = sumShortestPaths();
            System.out.println("Sum = " + sumShortestPath);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static boolean hasGalaxies(char[] line) {
        for (char a : line) if (a == '#') return true;
        return false;
    }

    private static void lookForExpansionX() {
        int expansion = 0;
        int x = 0;
        for (; x < _maxx; x++) {
            boolean hasGalaxies = false;
            for (int y = 0; y < _maxy; y++) {
                if (_map[y][x] == '#') {
                    hasGalaxies = true;
                    break;
                }
            }
            if (!hasGalaxies) _expandedX[expansion++] = x;
        }
    }

    private static int findGalaxies() {
        int nrOfGalaxy = 0;
        for (int y = 0; y < _maxy; y++) {
            for (int x = 0; x < _maxx; x++) {
                if (_map[y][x] == '#') {
                    _galaxies.put(nrOfGalaxy, new Galaxy(nrOfGalaxy, getVirtualX(x), getVirtualY(y)));
                    nrOfGalaxy++;
                }
            }
        }
        return nrOfGalaxy;
    }

    private static long getVirtualX(int x) {
        long newx = x;
        for (int ex = 0; _expandedX[ex] > 0; ex++) {
            if (x < _expandedX[ex]) return newx;
            newx += _expansion;
        }
        return newx;
    }

    private static long getVirtualY(int y) {
        long newy = y;
        for (int ex = 0; _expandedY[ex] > 0; ex++) {
            if (y < _expandedY[ex]) return newy;
            newy += _expansion;
        }
        return newy;
    }

    private static long sumShortestPaths() {
        long sum = 0;
        int nrOfDistances = 0;
        for (int galaxynrSrc = 0; galaxynrSrc < _galaxies.keySet().size() - 1; galaxynrSrc++) {
            for (int galaxynrDest = galaxynrSrc + 1; galaxynrDest < _galaxies.keySet().size(); galaxynrDest++) {
                Galaxy galaxySrc = _galaxies.get(galaxynrSrc);
                Galaxy galaxyDest = _galaxies.get(galaxynrDest);
                long distance = calcShortestPath(galaxySrc.x, galaxySrc.y, galaxyDest.x, galaxyDest.y, 0);
                System.out.printf("%02d: Galaxy %d (%d,%d) to %d (%d,%d) is %d%n", nrOfDistances++,
                        galaxySrc.number, galaxySrc.y + 1,
                        galaxySrc.x + 1, galaxyDest.number, galaxyDest.y + 1, galaxyDest.x + 1, distance);
                sum += distance;
            }
        }
        return sum;
    }

    private static long calcShortestPath(long x, long y, long destx, long desty, long distance) {
        return Math.abs(desty - y) + Math.abs(destx - x);
    }

    static class Galaxy {
        int number;
        long x;
        long y;

        public Galaxy(int number, long x, long y) {
            this.number = number;
            this.x = x;
            this.y = y;
        }
    }
}

