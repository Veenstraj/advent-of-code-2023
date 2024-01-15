package org.example;

import java.awt.*;

public class Vector {
    private final int id;

    private final Point point1;
    private Point point2;
    private final Dir direction;
    private final int length;
    private Dir inside;
    private final int xmin, xmax, ymin, ymax;

    // (0,0) is in upper left corner
    public Vector(int id, Point point1, Dir dir, int length) {
        this.id = id;
        this.point1 = point1;

        this.direction = dir;
        this.length = length;
        switch (dir) {
            case w -> this.point2 = new Point(point1.x - length, point1.y);
            case e -> this.point2 = new Point(point1.x + length, point1.y);
            case n -> this.point2 = new Point(point1.x, point1.y - length);
            case s -> this.point2 = new Point(point1.x, point1.y + length);
        }
        xmin = Math.min(point1.x, point2.x);
        xmax = Math.max(point1.x, point2.x);
        ymin = Math.min(point1.y, point2.y);
        ymax = Math.max(point1.y, point2.y);
//        System.out.printf("Vector %d: %s -> %s%n", id, point1, point2);
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public Dir getDirection() {
        return direction;
    }

    public int getId() {
        return id;
    }

    public PointPlus intersect(Vector that) {
        // Function to find the intersection point of two vectors
        double x1 = this.point1.x, y1 = this.point1.y;
        double x2 = this.point2.x, y2 = this.point2.y;
        double x3 = that.point1.x, y3 = that.point1.y;
        double x4 = that.point2.x, y4 = that.point2.y;

        // Calculate the parameter values at the intersection point
        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) /
                ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

        double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) /
                ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

        // Check if the vectors intersect within their respective parameter ranges
        if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
            return new PointPlus(
                    new Point((int) Math.floor((this.point1.x + t * (this.point2.x - this.point1.x) + 0.5)), (int) Math.floor((this.point1.y + t * (this.point2.y - this.point1.y) + 0.5))),
                    that);
        }
        return null;  // Vectors do not intersect
    }

    public boolean isXOnVector(int x) {
        return (x >= xmin && x <= xmax);
    }

    public boolean isYOnVector(int y) {
        return y >= ymin && y <= ymax;
    }

    public int getLength() {
        return length;
    }

    private Dir getDirection(Point point1, Point point2) {
        if (point1.x < point2.x) return Dir.e;
        if (point1.x > point2.x) return Dir.w;
        if (point1.y < point2.y) return Dir.s;
        return Dir.n;
    }

    public Dir getInside() {
        return inside;
    }

    public void setInside(Dir inside) {
        this.inside = inside;
    }

    public void setInside(Vector previous) {
        switch (previous.getDirection()) {
            case w -> {
                switch (this.direction) {
                    case n -> this.inside = (previous.inside == Dir.n ? Dir.e : Dir.w);
                    case s -> this.inside = (previous.inside == Dir.n ? Dir.w : Dir.e);
                }
            }
            case e -> {
                switch (this.direction) {
                    case n -> this.inside = (previous.inside == Dir.n ? Dir.w : Dir.e);
                    case s -> this.inside = (previous.inside == Dir.n ? Dir.e : Dir.w);
                }
            }
            case n -> {
                switch (this.direction) {
                    case w -> this.inside = (previous.inside == Dir.w ? Dir.s : Dir.n);
                    case e -> this.inside = (previous.inside == Dir.w ? Dir.n : Dir.s);
                }
            }
            case s -> {
                switch (this.direction) {
                    case w -> this.inside = (previous.inside == Dir.w ? Dir.n : Dir.s);
                    case e -> this.inside = (previous.inside == Dir.w ? Dir.s : Dir.n);
                }
            }
        }
    }

    public enum Dir {
        w(2), e(0), n(3), s(1);
        public final int number;

        private Dir(int number) {
            this.number = number;
        }
    }

    public static class PointPlus {
        public Point point;
        public Vector vector;

        public PointPlus(Point point, Vector vector) {
            this.point = point;
            this.vector = vector;
        }
    }
}
