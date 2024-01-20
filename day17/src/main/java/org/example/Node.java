package org.example;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// The Class to handle nodes
class Node {
    private final int id;

    private final int x;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private final int y;

    private List<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    @Override
    public String toString() {
        return "\nNode{" +
                "\n id=" + id +
                ",\n x=" + x +
                ",\n y=" + y +
                ",\n adjacent = " + showAdjacent(adjacentNodes) +
                ",\n shortestPath=" + showShortestPath(shortestPath) +
                ",\n distance=" + distance +
                '}';
    }

    private static String showShortestPath(List<Node> shortestPath) {
        StringBuilder result = new StringBuilder();
        for (Node node : shortestPath) {
            result.append(" ").append(node.getId()).append("(").append(node.distance).append(")");
        }
        return result.toString();
    }

    private static String showAdjacent(Map<Node, Integer> adjacentNodes) {
        StringBuilder result = new StringBuilder();
        for (Node node : adjacentNodes.keySet()) {
            result.append(" ").append(node.getId());
        }
        return result.toString();
    }

    //   @Override
    //   public int compareTo(Node o) {
    //       return this.id - o.getId();
    //   }
}
