package com.adventofcode.day17;

public enum Direction {

    EAST(1, 0), // op dezelfde regel naar rechts
    WEST(-1, 0), // op dezelfde regel naar links
    SOUTH(0, 1), // in dezelfde kolom naar beneden
    NORTH(0, -1);   // in dezelfde kolom omhoog

    private final int x;
    private final int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Direction turnClockwise() {
        return switch (this) {
            case NORTH -> EAST;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            case EAST -> SOUTH;
        };
    }

    public Direction turnAntiClockwise() {
        return switch (this) {
            case NORTH -> WEST;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
            case EAST -> NORTH;
        };
    }
}
