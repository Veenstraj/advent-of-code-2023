package org.example;

public class Sample {

    public static void main(String[] args) {
        // Define the first vector
        double x1 = 1, y1 = 2;  // Starting point
        double x2 = 4, y2 = 5;  // Ending point

        // Define the second vector
        double x3 = 2, y3 = 1;  // Starting point
        double x4 = 5, y4 = 4;  // Ending point

        // Calculate the intersection point
        double[] intersectionPoint = findIntersection(x1, y1, x2, y2, x3, y3, x4, y4);

        // Check if the vectors intersect
        if (intersectionPoint != null) {
            System.out.println("Vectors intersect at point (" + intersectionPoint[0] + ", " + intersectionPoint[1] + ")");
        } else {
            System.out.println("Vectors do not intersect");
        }
    }

    // Function to find the intersection point of two vectors
    private static double[] findIntersection(double x1, double y1, double x2, double y2,
                                             double x3, double y3, double x4, double y4) {
        double[] intersectionPoint = new double[2];

        // Calculate the parameter values at the intersection point
        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) /
                ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

        double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) /
                ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

        // Check if the vectors intersect within their respective parameter ranges
        if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
            intersectionPoint[0] = x1 + t * (x2 - x1);
            intersectionPoint[1] = y1 + t * (y2 - y1);
            return intersectionPoint;
        } else {
            return null;  // Vectors do not intersect
        }
    }
}

