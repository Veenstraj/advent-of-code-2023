package org.example;

public class Vector {

    private Coord coord1;
    private Coord coord2;
    private Direction direction;

    public Vector(Coord coord1, Coord coord2) {
        this.coord1 = coord1;
        this.coord2 = coord2;
        this.direction = getDirection(coord1, coord2);
    }

    // (0,0) is in upper left corner
    public Vector(Coord coord1, Direction dir, long nrOfsteps) {
        this.coord1 = coord1;
        this.direction = dir;
        switch (dir) {
            case w -> this.coord2 = new Coord(coord1.x - nrOfsteps, coord1.y);
            case e -> this.coord2 = new Coord(coord1.x + nrOfsteps, coord1.y);
            case n -> this.coord2 = new Coord(coord1.x, coord1.y - nrOfsteps);
            case s -> this.coord2 = new Coord(coord1.x, coord1.y + nrOfsteps);
        }

    }

    public boolean isOnVector(Coord coord) {
        return isXOnVector(coord.x) && isYOnVector(coord.y);
    }

    public long getDistance(Coord coord) {
        switch (getDirection(coord, coord1)) {
            case n, s -> {
                if (isXOnVector(coord.x)) {
                    return Math.abs(coord.y - coord1.y);
                }
            }
            case e, w -> {
                if (isYOnVector(coord.y)) {
                    return Math.abs(coord.y - coord1.y);
                }
            }
        }
        return Long.MAX_VALUE;
    }

    private boolean isXOnVector(long x) {
        long xmin = Math.min(coord1.x, coord2.x);
        long xmax = Math.max(coord1.x, coord2.x);
        return (x >= xmin && x <= xmax);

    }

    private boolean isYOnVector(long y) {
        long ymin = Math.min(coord1.y, coord2.y);
        long ymax = Math.max(coord1.y, coord2.y);
        return y >= ymin && y <= ymax;
    }

    private Direction getDirection(Coord coord1, Coord coord2) {
        if (coord1.x < coord2.x) return Direction.e;
        if (coord1.x > coord2.x) return Direction.w;
        if (coord1.y < coord2.y) return Direction.s;
        return Direction.n;
    }

    public static enum Direction {
        w, e, n, s
    }

    public static class Coord {

        private long x;
        private long y;
        private long absx;
        private long absy;

        public Coord(long x, long y) {
            this.x = x;
            this.y = y;
            this.absx = Math.abs(x);
            this.absy = Math.abs(y);
        }

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }

        public long getAbsX() {
            return absx;
        }

        public long getAbsY() {
            return absy;
        }
    }
}
