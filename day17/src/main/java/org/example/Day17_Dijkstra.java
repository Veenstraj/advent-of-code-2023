package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day17_Dijkstra {
    private static final String inputfile = "./day17/target/classes/inputTst.txt";
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

            Graph graph = new Graph();


            for (int y = 0; y < _sizey; y++) {
                for (int x = 0; x < _sizex; x++) {
                    _nodes[x + y * _sizex] = new Node(x + y * _sizex, x, y);
                }
            }

            for (int y = 0; y < _sizey; y++) {
                for (int x = 0; x < _sizex; x++) {
                    if (!isOutside(x - 1, y))
                        _nodes[x + y * _sizex].addDestination(_nodes[x - 1 + y * _sizex], getMap(x - 1, y) - '0');
                    if (!isOutside(x + 1, y))
                        _nodes[x + y * _sizex].addDestination(_nodes[x + 1 + y * _sizex], getMap(x + 1, y) - '0');
                    if (!isOutside(x, y - 1))
                        _nodes[x + y * _sizex].addDestination(_nodes[x + (y - 1) * _sizex], getMap(x, y - 1) - '0');
                    if (!isOutside(x, y + 1))
                        _nodes[x + y * _sizex].addDestination(_nodes[x + (y + 1) * _sizex], getMap(x, y + 1) - '0');

                    graph.addNode(_nodes[x + y * _sizex]);
                }
            }

            dijkstra_algo(null, _nodes[0]);

            System.out.printf("%nsizeX=%d, sizeY=%d, maxId=%d%n", _sizex, _sizey, maxNodeId);

            System.out.printf("Shortest path = %s", _nodes[maxNodeId].getDistance().toString());
            printMap(maxNodeId);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static Graph dijkstra_algo(Graph graph, Node source) {
        source.setDistance(0);
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            if (currentNode == null) {
                //System.out.println("size=" + unsettledNodes.size());
                unsettledNodes = new HashSet<>();
            } else {
                //System.out.printf("Current node=%d%n", currentNode.getId());
                unsettledNodes.remove(currentNode);
                for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                    Node adjacentNode = adjacencyPair.getKey();
                    Integer edgeWeight = adjacencyPair.getValue();

                    if (!settledNodes.contains(adjacentNode)) {
                        if (calculateMinimumDistance(adjacentNode, edgeWeight, currentNode)) {
                            unsettledNodes.add(adjacentNode);
                        }
                    }
                }
                settledNodes.add(currentNode);
            }
        }
        return graph;
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

    private static boolean calculateMinimumDistance(Node evaluationNode, Integer edgeWeight, Node sourceNode) {
        boolean result = false;
        //System.out.printf(" call (%d, %d, %d)", evaluationNode.getId(), edgeWeight, sourceNode.getId());
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeight < evaluationNode.getDistance()) {
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            //System.out.printf(" srcSp=" + getShortestPath(sourceNode.getShortestPath()) + ", sp=" + getShortestPath(shortestPath));
            if (checkOnDirectionOk(shortestPath, sourceNode)) {
                evaluationNode.setDistance(sourceDistance + edgeWeight);
                //System.out.printf(" %d->%d: newDist of %d=%d ", sourceNode.getId(), evaluationNode.getId(), evaluationNode.getId(), sourceDistance + edgeWeight);
                shortestPath.add(sourceNode);
                evaluationNode.setShortestPath(shortestPath);
                result = true;
                //System.out.printf(", Path of %d = " + getShortestPath(evaluationNode.getShortestPath()) + "%n", evaluationNode.getId());
            } else {
                //System.out.printf("%d->%d: CheckOnDirectionOk=false%n", sourceNode.getId(), evaluationNode.getId());
            }
        } else {
            //System.out.println(" path too long");
        }
        return result;
    }

    private static String getShortestPath(List<Node> sp) {
        int size = Math.min(sp.stream().map(Node::getId).toList().size(), 5);
        if (size == 5) {
            return sp.stream().map(Node::getId).toList().subList(0, size) + "..";
        } else {
            return sp.stream().map(Node::getId).toList().subList(0, size).toString();

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
