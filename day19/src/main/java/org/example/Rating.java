package org.example;

public record Rating(int x, int m, int a, int s) {
    public int sum() {
        return x + m + a + s;
    }
}
