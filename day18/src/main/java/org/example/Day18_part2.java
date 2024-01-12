package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day18_part2 {
    private static final String inputfile = "./day18/target/classes/input.txt";

    private static final Set<Vector> _vectorList = new HashSet<>();

    private static long _minx = Long.MAX_VALUE;
    private static long _maxx = 0;
    private static long _miny = Long.MAX_VALUE;
    private static long _maxy = 0;

    public static void main(String[] args) {

        opgave2();
    }

    public static void opgave2() {
        BufferedReader reader;

        try {
            Vector.Point currentPoint = new Vector.Point(0, 0);
            reader = new BufferedReader(new FileReader(inputfile));
            String line = reader.readLine();
            int sum = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
//                    String richting = line.trim().split(" ")[0];
//                    int nrOfMeters = Integer.valueOf(line.trim().split(" ")[1]);
                    String color = line.trim().split(" ")[2];
                    String richting = color.substring(7, 8);
                    int nrOfMeters = Integer.decode("0x" + color.substring(2, 7));
                    switch (richting) {
                        case "0", "R" -> { //R (east)
                            Vector vector = new Vector(currentPoint, Vector.Direction.e, nrOfMeters);
                            _vectorList.add(vector);
                            currentPoint = vector.getPoint2();
                        }
                        case "2", "L" -> { //L (west)
                            Vector vector = new Vector(currentPoint, Vector.Direction.w, nrOfMeters);
                            _vectorList.add(vector);
                            currentPoint = vector.getPoint2();
                        }
                        case "3", "U" -> { //U (north)
                            Vector vector = new Vector(currentPoint, Vector.Direction.n, nrOfMeters);
                            _vectorList.add(vector);
                            currentPoint = vector.getPoint2();
                        }
                        case "1", "D" -> { //D (south)
                            Vector vector = new Vector(currentPoint, Vector.Direction.s, nrOfMeters);
                            _vectorList.add(vector);
                            currentPoint = vector.getPoint2();
                        }
                    }
                    _maxx = Math.max(currentPoint.getX(), _maxx);
                    _maxy = Math.max(currentPoint.getY(), _maxy);
                    _minx = Math.min(currentPoint.getX(), _minx);
                    _miny = Math.min(currentPoint.getY(), _miny);
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
//            digout();
//            sum = printMap();
            System.out.printf("minx=%d, miny=%d, maxx=%d, maxy=%d%n", _minx, _miny, _maxx, _maxy);

            System.out.println("sum=" + sum);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
