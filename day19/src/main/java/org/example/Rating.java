package org.example;

import java.util.HashMap;
import java.util.Map;

public class Rating {
    Map<String, Integer> xmasUpper = new HashMap<>();
    Map<String, Integer> xmasLower = new HashMap<>();

    Rating(int x, int m, int a, int s) {
        this.xmasUpper.put("x", x);
        this.xmasUpper.put("m", m);
        this.xmasUpper.put("a", a);
        this.xmasUpper.put("s", s);

        this.xmasLower.put("x", 1);
        this.xmasLower.put("m", 1);
        this.xmasLower.put("a", 1);
        this.xmasLower.put("s", 1);
    }

    public Rating clone() {
        Rating rating = new Rating(this.xmasUpper.get("x"),
                this.xmasUpper.get("m"),
                this.xmasUpper.get("a"),
                this.xmasUpper.get("s"));
        rating.xmasLower.put("x", this.xmasLower.get("x"));
        rating.xmasLower.put("m", this.xmasLower.get("m"));
        rating.xmasLower.put("a", this.xmasLower.get("a"));
        rating.xmasLower.put("s", this.xmasLower.get("s"));
        return rating;
    }

    @Override
    public String toString() {
        return "Rating x[" + xmasLower.get("x") + ".." + xmasUpper.get("x") + "]" +
                "m[" + xmasLower.get("m") + ".." + xmasUpper.get("m") + "]" +
                "a[" + xmasLower.get("a") + ".." + xmasUpper.get("a") + "]" +
                "s[" + xmasLower.get("s") + ".." + xmasUpper.get("s") + "]";
    }

    public int x() {
        return this.xmasUpper.get("x");
    }

    public int m() {
        return this.xmasUpper.get("m");
    }

    public int a() {
        return this.xmasUpper.get("a");
    }

    public int s() {
        return this.xmasUpper.get("s");
    }

    public int lx() {
        return this.xmasLower.get("x");
    }

    public int lm() {
        return this.xmasLower.get("m");
    }

    public int la() {
        return this.xmasLower.get("a");
    }

    public int ls() {
        return this.xmasLower.get("s");
    }

    public int sum() {
        return x() + m() + a() + s();
    }

    public long product() {
        return (long) (x() - lx() + 1) * (m() - lm() + 1) * (a() - la() + 1) * (s() - ls() + 1);
    }

    public void narrowDown(String rating, int lower, int upper) {
        if (upper < lower) System.out.printf("Upper %d is lower than lower %d @ %s%n", upper, lower, rating);

        if (lower >= xmasLower.get(rating) && lower <= xmasUpper.get(rating)) xmasLower.put(rating, lower);
        if (upper >= xmasLower.get(rating) && upper <= xmasUpper.get(rating)) xmasUpper.put(rating, upper);
    }

}
