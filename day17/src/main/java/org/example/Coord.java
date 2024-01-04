package org.example;

public class Coord implements Comparable<Coord> {
    int x;
    int y;

    int distance;

    public Coord(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    @Override
    public int compareTo(Coord o) {
        return this.distance - o.distance;
    }
}
