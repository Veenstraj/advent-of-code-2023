package com.adventofcode.day17;

public record Position(int x, int y) {
    public Position next(Direction direction) {
        return new Position(x() + direction.getX(), y() + direction.getY());
    }
}
