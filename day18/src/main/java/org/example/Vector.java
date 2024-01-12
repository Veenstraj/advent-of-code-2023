package org.example;

public class Vector {

    private Point point1;
    private Point point2;
    private Direction direction;

    public Vector(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
        this.direction = getDirection(point1, point2);
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public Direction getDirection() {
        return direction;
    }

    // (0,0) is in upper left corner
    public Vector(Point point1, Direction dir, long nrOfsteps) {
        this.point1 = point1;
        this.direction = dir;
        switch (dir) {
            case w -> this.point2 = new Point(point1.x - nrOfsteps, point1.y);
            case e -> this.point2 = new Point(point1.x + nrOfsteps, point1.y);
            case n -> this.point2 = new Point(point1.x, point1.y - nrOfsteps);
            case s -> this.point2 = new Point(point1.x, point1.y + nrOfsteps);
        }

    }

    public Point intersect(Vector other) {
        // Function to find the intersection point of two vectors

        // Calculate the parameter values at the intersection point
        long t = ((this.point1.x - other.point1.x) * (other.point1.y - other.point2.y)
                - (this.point1.y - other.point1.y) * (other.point1.x - other.point2.x)) /
                ((this.point1.x - this.point2.x) * (other.point1.y - other.point2.y)
                        - (this.point1.y - this.point2.y) * (other.point1.x - other.point2.x));

        long u = -((this.point1.x - this.point2.x) * (this.point1.y - other.point1.y)
                - (this.point1.y - this.point2.y) * (this.point1.x - other.point1.x)) /
                ((this.point1.x - this.point2.x) * (other.point1.y - other.point2.y)
                        - (this.point1.y - this.point2.y) * (other.point1.x - other.point2.x));

        //            // Check if the vectors intersect within their respective parameter ranges
        if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
            return new Point(this.point1.x + t * (this.point2.x - this.point1.x), this.point1.y + t * (this.point2.y - this.point1.y));
        } else {
            return null;  // Vectors do not intersect
        }

//        private static double[] findIntersection(double x1, double y1, double x2, double y2,
//        double x3, double y3, double x4, double y4) {
//            double[] intersectionPoint = new double[2];
//
//            // Calculate the parameter values at the intersection point
//            double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) /
//                    ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
//
//            double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) /
//                    ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
//
//            // Check if the vectors intersect within their respective parameter ranges
//            if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
//                intersectionPoint[0] = x1 + t * (x2 - x1);
//                intersectionPoint[1] = y1 + t * (y2 - y1);
//                return intersectionPoint;
//            } else {
//                return null;  // Vectors do not intersect
//            }
//        }


    }

    public boolean isOnVector(Point point) {
        return isXOnVector(point.x) && isYOnVector(point.y);
    }

    private boolean isXOnVector(long x) {
        long xmin = Math.min(point1.x, point2.x);
        long xmax = Math.max(point1.x, point2.x);
        return (x >= xmin && x <= xmax);

    }

    private boolean isYOnVector(long y) {
        long ymin = Math.min(point1.y, point2.y);
        long ymax = Math.max(point1.y, point2.y);
        return y >= ymin && y <= ymax;
    }

    private Direction getDirection(Point point1, Point point2) {
        if (point1.x < point2.x) return Direction.e;
        if (point1.x > point2.x) return Direction.w;
        if (point1.y < point2.y) return Direction.s;
        return Direction.n;
    }

    public static enum Direction {
        w, e, n, s
    }

    public static class Point {

        private long x;
        private long y;
        private long absx;
        private long absy;

        public Point(long x, long y) {
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
