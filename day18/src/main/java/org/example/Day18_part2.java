package org.example;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class Day18_part2 {
    private static final String inputfile = "./day18/target/classes/input.txt";

    private static final Set<Vector> _vectorList = new HashSet<>();

    private static int _minx = Integer.MAX_VALUE;
    private static int _maxx = 0;
    private static int _miny = Integer.MAX_VALUE;
    private static int _maxy = 0;

    public static void main(String[] args) {

        opgave(false); // true=opgave1, false=opgave2
    }

    public static void opgave(boolean opgave1) {
        BufferedReader reader;
        Point currentPoint = new Point(0, 0);
        Vector vector = null, prevVector = null;
        long sum = 0;
        int vectorId = 0;

        try {
            reader = new BufferedReader(new FileReader(inputfile));
            String line = reader.readLine();
            while (line != null) {
                if (line.trim().length() > 0) {
                    String richting;
                    int nrOfMeters;
                    String color = line.trim().split(" ")[2];
                    if (opgave1) {
                        richting = line.trim().split(" ")[0];
                        nrOfMeters = Integer.parseInt(line.trim().split(" ")[1]);
                    } else { // opgave 2
                        richting = color.substring(7, 8);
                        nrOfMeters = Integer.decode("0x" + color.substring(2, 7));
                    }
                    // System.out.printf("#meters=%d ", nrOfMeters);
                    vector = createVector(richting, nrOfMeters, vectorId, prevVector, currentPoint);
                    if (prevVector != null) {
                        vector.setInside(prevVector); // Inside geeft aan waar de binnenkant is aan de rand van de vijver
                    }
                    prevVector = vector;
                    _vectorList.add(vector);
                    currentPoint = vector.getPoint2();
                    _maxx = (int) Math.max(currentPoint.getX() + 2, _maxx);
                    _maxy = (int) Math.max(currentPoint.getY() + 2, _maxy);
                    _minx = (int) Math.min(currentPoint.getX() - 1, _minx);
                    _miny = (int) Math.min(currentPoint.getY() - 1, _miny);
                    vectorId++;
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
            System.out.printf("minx=%d, miny=%d, maxx=%d, maxy=%d%n", _minx, _miny, _maxx, _maxy);
            sum = digout(); // 14-01 82712743088415 (652 s) too low  - 15-01 82712746433310 (108 s) ok
            System.out.println("\nsum=" + sum);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Vector createVector(String richting, int nrOfMeters, int vectorId, Vector prevVector, Point currentPoint) {
        Vector vector = null;
        switch (richting) {
            case "0", "R" -> { //R (east)
                vector = new Vector(vectorId, currentPoint, Vector.Dir.e, nrOfMeters);
                if (prevVector == null) vector.setInside(Vector.Dir.s);  // Aanname: rechtsom
            }
            case "2", "L" -> { //L (west)
                vector = new Vector(vectorId, currentPoint, Vector.Dir.w, nrOfMeters);
                if (prevVector == null) vector.setInside(Vector.Dir.n);  // Aanname: rechtsom
            }
            case "3", "U" -> { //U (north)
                vector = new Vector(vectorId, currentPoint, Vector.Dir.n, nrOfMeters);
                if (prevVector == null) vector.setInside(Vector.Dir.e);  // Aanname: rechtsom
            }
            case "1", "D" -> { //D (south)
                vector = new Vector(vectorId, currentPoint, Vector.Dir.s, nrOfMeters);
                if (prevVector == null) vector.setInside(Vector.Dir.w);  // Aanname: rechtsom
            }
        }
        return vector;
    }

    private static long digout() {
        long counter = 0;
        long dateTime = System.currentTimeMillis();
        long insideBlocks = 0;

        for (int y = _miny; y <= _maxy; y++) {
//            System.out.printf("%d: ", y);
            Vector entireX = new Vector(-1, new Point(_minx, y), Vector.Dir.e, _maxx - _minx);

            List<Vector.PointPlus> intersects = getIntersects(entireX, y);
            long delta = determineInsideBocks(intersects);
            insideBlocks += delta;

//            System.out.printf("delta=%d, innerBlocks=%d%n", delta, insideBlocks);
            if (++counter % 100000 == 0) {
                System.out.printf("\r%d (%d)", y, insideBlocks);
            }
        }
        long timenow = System.currentTimeMillis();
        System.out.printf("%d ms elapsed%n", timenow - dateTime);
        return insideBlocks;
    }

    private static List<Vector.PointPlus> getIntersects(Vector entireX, int y) {
        return _vectorList.stream()
                .filter(v -> v.isYOnVector(y))
                .map(entireX::intersect)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(n -> n.point.x))
                .toList();
    }

    private static long determineInsideBocks(List<Vector.PointPlus> intersects) {
        int lastx = _minx;
        boolean isInside = false;
        long intersect = 0;
        long insideBlocks = 0;

        for (Vector.PointPlus pp : intersects) {
            if (isInside) {
                intersect = pp.point.x - lastx + 1; //dikte vector
                insideBlocks += intersect;
                lastx = pp.point.x + 1;
            } else {
                intersect = pp.point.x - lastx - 1;
                lastx = pp.point.x;
            }
//                System.out.printf(" (%d,%d)%s d=%d (%b), ", pp.point.x, pp.point.y, pp.vector.getDirection(), intersect, isInside);
            isInside = pp.vector.getInside() == Vector.Dir.e; // set for next iteration
            // when distance is an east-west vector, it is always inside
            if (pp.vector.getPoint1().equals(pp.point) && findVectorWest(pp.point)) {
//                    System.out.printf(" vw ");
                isInside = true;
            }
            if (pp.vector.getPoint2().equals(pp.point) && findVectorEast(pp.point)) {
//                System.out.printf(" ve ");
                isInside = true;
            }
        }
        return insideBlocks;
    }

    private static boolean findVectorWest(Point point) {
        // find vector going west with same end-point
        return _vectorList.stream()
                .filter(v -> v.getPoint2().equals(point))
                .filter(v -> v.getDirection() == Vector.Dir.w)
                .findFirst()
                .orElse(null) != null;
    }

    private static boolean findVectorEast(Point point) {
        return _vectorList.stream()
                .filter(v -> v.getPoint1().equals(point))
                .filter(v -> v.getDirection() == Vector.Dir.e)
                .findFirst()
                .orElse(null) != null;
    }
}
