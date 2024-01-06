package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day17_Dijkstra {
    private static final String inputfile = "./day17/target/classes/input.txt";
    private static final char[][] _map = new char[145][145];
    private static int _sizex;
    private static int _sizey;

    private static Node[] _nodes;

    public static void main(String[] args) {

        opgave1();
    }

    public static void opgave1() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(inputfile));
            String line = reader.readLine();

            _sizey = 0;
            while (line != null) {
                if (line.trim().length() > 0) {
                    _map[_sizey++] = line.trim().toCharArray();
                }

                // read next line
                line = reader.readLine();
            }
            reader.close();

            _sizex = _map[0].length;
            _nodes = new Node[_sizey * _sizex];
            int maxNodeId = _sizex * _sizey - 1;
            for (int y = 0; y < _sizey; y++) {
                for (int x = 0; x < _sizex; x++) {
                    _nodes[x + y * _sizex] = new Node(x + y * _sizex, x, y);
                }
            }

            Graph graph = new Graph();  // Deze MOET hier geinstantieerd worden anders andere uitkomst????

            for (int y = 0; y < _sizey; y++) {
                for (int x = 0; x < _sizex; x++) {
                    SortedSet<Node> adjacentNodes = getAdjacentNodes(x, y);
                    for (Node adjacentNode : adjacentNodes) {
                        _nodes[x + y * _sizex].addDestination(adjacentNode, getMap(adjacentNode.getX(), adjacentNode.getY()) - '0');
                    }
                }
            }

            System.out.printf("Node Shortest path = %s", _nodes[maxNodeId].toString());

            dijkstra_algo(graph, _nodes[0]);

            System.out.printf("%nsizeX=%d, sizeY=%d, maxId=%d%n", _sizex, _sizey, maxNodeId);

            System.out.printf("Node Shortest path = %s", _nodes[maxNodeId].toString());
            printMap(maxNodeId);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void dijkstra_algo(Graph graph, Node source) {
        source.setDistance(0);
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            if (currentNode == null) {
                unsettledNodes = new HashSet<>(); // clear unsettled
            } else {
                unsettledNodes.remove(currentNode);
                for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                    Node adjacentNode = adjacencyPair.getKey();
                    Integer edgeWeight = adjacencyPair.getValue();

                    if (!settledNodes.contains(adjacentNode)) {
                        calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                        unsettledNodes.add(adjacentNode);
                    }
                }
                settledNodes.add(currentNode);
            }
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeight, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeight < evaluationNode.getDistance()) {
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            if (checkOnDirectionOk(shortestPath, sourceNode)) {
                evaluationNode.setDistance(sourceDistance + edgeWeight);
                shortestPath.add(sourceNode);
                evaluationNode.setShortestPath(shortestPath);
            }
        }
    }

    private static boolean checkOnDirectionOk(LinkedList<Node> shortestPath, Node newNode) {

        int size = shortestPath.size();
        if (size < 4) return true;
        Node node1 = shortestPath.get(size - 1);
        char dir1 = getDirection(newNode.getX(), newNode.getY(), node1.getX(), node1.getY());
        Node node2 = shortestPath.get(size - 2);
        char dir2 = getDirection(node1.getX(), node1.getY(), node2.getX(), node2.getY());
        Node node3 = shortestPath.get(size - 3);
        char dir3 = getDirection(node2.getX(), node2.getY(), node3.getX(), node3.getY());
        Node node4 = shortestPath.get(size - 4);
        char dir4 = getDirection(node3.getX(), node3.getY(), node4.getX(), node4.getY());
        boolean result = dir1 != dir2 || dir2 != dir3 || dir3 != dir4;
//        if (!result) System.out.printf("Node %d gaat ook naar %c -> NIET toevoegen aan shortest path.%n", newNode.getId(), dir1);
        return result;
    }

    private static SortedSet<Node> getAdjacentNodes(int x, int y) {
        SortedSet<Node> adjacentNodes = new TreeSet<>();

        determineAdjacent(adjacentNodes, x + 1, y);
        determineAdjacent(adjacentNodes, x - 1, y);
        determineAdjacent(adjacentNodes, x, y + 1);
        determineAdjacent(adjacentNodes, x, y - 1);
        return adjacentNodes;
    }

    private static void determineAdjacent(SortedSet<Node> set, int x, int y) {
        if (!isOutside(x, y)) {
            set.add(_nodes[x + y * _sizex]);
        }
    }

    private static char getDirection(int x, int y, int newx, int newy) {
        if (newx < x) return 'w';
        if (newx > x) return 'e';
        if (newy < y) return 'n';
        return 's';
    }


    private static char getMap(int x, int y) {
        return _map[y][x];
    }

    private static boolean isOutside(int x, int y) {
        return x < 0 || y < 0 || y >= _sizey || x >= _sizex;
    }

    private static void printMap(int nodeId) {
        Node node = _nodes[nodeId];
        System.out.println("\n" + nodeId);
        for (int y = 0; y < _sizey; y++) {
            for (int x = 0; x < _sizex; x++) {
                boolean found = false;
                for (Node pathNode : node.getShortestPath()) {
                    if (x == pathNode.getX() && y == pathNode.getY()) {
                        System.out.print("#");
                        found = true;
                        break;
                    }
                }
                if (!found) System.out.print(getMap(x, y));
            }
            System.out.println();
        }
    }
}