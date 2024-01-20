package org.example;

public class Rating {
    int x;
    int m;
    int a;
    int s;
    int lowerx;
    int lowerm;
    int lowera;
    int lowers;

    Rating(int x, int m, int a, int s) {
        this.x = x;
        this.m = m;
        this.a = a;
        this.s = s;

        this.lowerx = 1;
        this.lowerm = 1;
        this.lowera = 1;
        this.lowers = 1;
    }

    public Rating clone() {
        Rating rating = new Rating(x, m, a, s);
        rating.lowerx = this.lowerx;
        rating.lowerm = this.lowerm;
        rating.lowera = this.lowera;
        rating.lowers = this.lowers;
        return rating;
    }

    @Override
    public String toString() {
        return "Rating x[" + lowerx + ".." + x + "]" +
                "m[" + lowerm + ".." + m + "]" +
                "a[" + lowera + ".." + a + "]" +
                "s[" + lowers + ".." + s + "]";
    }

    public int x() {
        return x;
    }

    public int m() {
        return m;
    }

    public int a() {
        return a;
    }

    public int s() {
        return s;
    }

    public int getLowerx() {
        return lowerx;
    }

    public void setLowerx(int lowerx) {
        this.lowerx = lowerx;
    }

    public int getLowerm() {
        return lowerm;
    }

    public void setLowerm(int lowerm) {
        this.lowerm = lowerm;
    }

    public int getLowera() {
        return lowera;
    }

    public void setLowera(int lowera) {
        this.lowera = lowera;
    }

    public int getLowers() {
        return lowers;
    }

    public void setLowers(int lowers) {
        this.lowers = lowers;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int sum() {
        return x + m + a + s;
    }

    public long product() {
        return (long) (x - lowerx + 1) * (m - lowerm + 1) * (a - lowera + 1) * (s - lowers + 1);
    }

    public void setAllZero() {
        x = m = s = a = 0;
        lowerx = lowerm = lowera = lowers = 1;
    }

    public boolean narrowDown(String rating, int lower, int upper) {
        if (upper < lower) {
            System.out.printf("Upper %d is lower than lower %d @ %s%n", upper, lower, rating);
        }
        boolean modified = false;
        switch (rating) {
            case "x" -> {
                if (lower >= lowerx && lower <= x) {
                    lowerx = lower;
                    modified = true;
                }
                if (upper >= lowerx && upper <= x) {
                    x = upper;
                    modified = true;
                }
            }
            case "m" -> {
                if (lower >= lowerm && lower <= m) {
                    lowerm = lower;
                    modified = true;
                }
                if (upper >= lowerm && upper <= m) {
                    m = upper;
                    modified = true;
                }
            }
            case "a" -> {
                if (lower >= lowera && lower <= a) {
                    lowera = lower;
                    modified = true;
                }
                if (upper >= lowera && upper <= a) {
                    a = upper;
                    modified = true;
                }

            }
            case "s" -> {
                if (lower >= lowers && lower <= s) {
                    lowers = lower;
                    modified = true;
                }
                if (upper >= lowers && upper <= s) {
                    s = upper;
                    modified = true;
                }
            }
        }
        return modified;
    }

//    public boolean inRange(String rating, int value) {
//        switch (rating) {
//            case "x" -> {
//                return (value >= lowerx && value <= x);
//            }
//            case "m" -> {
//                return (value >= lowerm && value <= m);
//            }
//            case "a" -> {
//                return (value >= lowera && value <= a);
//            }
//            case "s" -> {
//                return (value >= lowers && value <= s);
//            }
//        }
//    }
}
