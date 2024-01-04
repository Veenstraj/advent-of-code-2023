package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Day17 {
    private static final String inputfile = "./day17/target/classes/input.txt";
    private static final char[][] _map = new char[145][145];
    private static int _sizex;
    private static int _sizey;
    private static char[][] _visited;

    private static boolean _print = false;

    private static int _shortestPath = Integer.MAX_VALUE;

    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(inputfile));
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
            zoekMinimaleHeatloss(0, 0, 0, 's', 0);

            System.out.println("Minimal path=" + _shortestPath);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void zoekMinimaleHeatloss(int x, int y, int totalDistance, char direction, int dirCount) {
//        printCoord(x, y);
//        System.out.printf("%c total=%d dir=%c%d%n", getMap(x, y), totalDistance, direction, dirCount);

        setVst(direction, x, y);

        Set<Coord> adjacentNodes = determineNextNotVisitedNodeShortestPath(x, y);
        for (Coord coord : adjacentNodes) {
            char newDirection = getDirection(x, y, coord.x, coord.y);
            int newDirCount = (newDirection == direction) ? dirCount + 1 : 0;
            if (newDirCount < 3) {
                int newDistance = totalDistance + coord.distance;
                if (newDistance < _shortestPath) {
                    if (coord.x == _sizex - 1 && coord.y == _sizey - 1) {
//                        printCoord(coord.x, coord.y);
//                        System.out.printf(" Endpoint reached.. distance=%d%n", newDistance);
                        if (newDistance < _shortestPath) _shortestPath = newDistance;
//                        printVisited("Visited");
                    } else {
                        zoekMinimaleHeatloss(coord.x, coord.y, newDistance, newDirection, newDirCount);
//                    System.out.printf("< (%d,%d) total=%d, depth=%d%n", y+1, x+1, totalDistance, depth);
                    }
                }
                if (x == 0 && y == 0) {
                    System.out.println("Terug bij oorsprong");
                    _print = true;
                }
            } else {
//                System.out.println("Meer dan 3 achtereen");
            }
        }
        setVst(x, y, (char) 0); // geef weer vrij
    }

    private static SortedSet<Coord> determineNextNotVisitedNodeShortestPath(int x, int y) {
        SortedSet<Coord> adjacentNodes = new TreeSet<>();

        determineAdjacent(adjacentNodes, x + 1, y);
        determineAdjacent(adjacentNodes, x - 1, y);
        determineAdjacent(adjacentNodes, x, y + 1);
        determineAdjacent(adjacentNodes, x, y - 1);
        return adjacentNodes;
    }

    private static void determineAdjacent(SortedSet<Coord> set, int x, int y) {
        if (!isOutside(x, y) && getVst(x, y) == (char) 0) {
            set.add(new Coord(x, y, getMap(x, y) - '0'));
        }
    }

    private static char getDirection(int x, int y, int newx, int newy) {
        if (newx < x) return 'w';
        if (newx > x) return 'e';
        if (newy < y) return 'n';
        return 's';
    }

    private static void setVst(int x, int y, char value) {
        if (!isOutside(x, y)) _visited[y][x] = value;
    }

    private static void setVst(char direction, int x, int y) {
        switch (direction) {
            case 'w' -> setVst(x, y, '<');
            case 'e' -> setVst(x, y, '>');
            case 'n' -> setVst(x, y, '^');
            case 's' -> setVst(x, y, 'v');
        }
    }

    private static void printVisited(String caption) {
        System.out.println(caption);
        for (int y = 0; y < _sizey; y++) {
            for (int x = 0; x < _sizex; x++) {
                System.out.print(String.valueOf(getVst(x, y)));
            }
            System.out.println();
        }
        System.out.println();
    }

    private static char getVst(int x, int y) {
        return _visited[y][x];
    }

    private static char getMap(int x, int y) {
        return _map[y][x];
    }

    private static boolean isOutside(int x, int y) {
        return x < 0 || y < 0 || y >= _sizey || x >= _sizex;
    }

    private static void printCoord(int x, int y) {
        System.out.print("(" + (y + 1) + "," + (x + 1) + ")");
    }
}
