package com.adventofcode.day17;

import java.util.LinkedList;
import java.util.List;

public class State {

    private Node node;
    private int heatLoss;

    private List<Node> shortestPath = new LinkedList<>();

    public Node getNode() {
        return node;
    }

    public int getHeatLoss() {
        return heatLoss;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public State(Node node, int heatLoss) {
        this.node = node;
        this.heatLoss = heatLoss;
    }

    public State(Node node, int heatLoss, List<Node> shortestPath) {
        this.node = node;
        this.heatLoss = heatLoss;
        this.shortestPath = shortestPath;
    }

    @Override
    public String toString() {
        return "State{" +
                "node=" + node +
                ", heatLoss=" + heatLoss +
                '}';
    }
}
