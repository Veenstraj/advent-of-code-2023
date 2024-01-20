package org.example;

public record Rule(int order, String rating, boolean greaterThan, int value, String destination) {

    public String apply(Rating rating) {
        int ratingvalue = 0;
        switch (this.rating()) {
            case "x" -> ratingvalue = rating.x();
            case "m" -> ratingvalue = rating.m();
            case "a" -> ratingvalue = rating.a();
            case "s" -> ratingvalue = rating.s();
            case "Z" -> {
                return this.destination;
            }
        }
        if (this.greaterThan() ? ratingvalue > this.value() : ratingvalue < this.value()) {
            return this.destination;
        }
        return "X"; // no match
    }

    @Override
    public String toString() {
        if (this.rating.equals("Z")) return "Rule " + this.destination;
        return String.format("Rule %s%s%d:%s",
                this.rating(), this.greaterThan() ? ">" : "<", this.value(), this.destination());
    }
}
