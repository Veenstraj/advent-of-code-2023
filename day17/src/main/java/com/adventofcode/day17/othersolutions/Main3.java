package com.adventofcode.day17.othersolutions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class Main3 {

    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    // 0 = boven 1 = rechts 2=onder 3 = links
    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/richardboersma/IdeaProjects/zelfstudie2/src/main/resources/com/adventofcode/day17/inputfile_full.txt"))) {
            int[][] field = new int[141][141];
            String regel;
            int y = 0;
            while ((regel = bufferedReader.readLine()) != null) {

                for (int x = 0; x < regel.length(); x++) {
                    field[y][x] = Integer.parseInt(regel.substring(x, x + 1));
                }
                y++;
            }

            return String.valueOf(dijkstra(field, true));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/richardboersma/IdeaProjects/zelfstudie2/src/main/resources/com/adventofcode/day17/inputfile_full.txt"))) {
            int[][] field = new int[141][141];
            String regel;
            int y = 0;
            while ((regel = bufferedReader.readLine()) != null) {

                for (int x = 0; x < regel.length(); x++) {
                    field[y][x] = Integer.parseInt(regel.substring(x, x + 1));
                }
                y++;
            }

            return String.valueOf(dijkstra(field, false));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static int dijkstra(int[][] grid, boolean part1) {
        Queue<Element> queue = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();
        int endX = grid[grid.length - 1].length - 1;
        int endY = grid.length - 1;

        Node eastStart = new Node(1, 0, 1, Element.EAST);
        Node southStart = new Node(0, 1, 1, Element.SOUTH);
        queue.add(new Element(eastStart, grid[0][1]));
        queue.add(new Element(southStart, grid[1][0]));

        while (!queue.isEmpty()) {
            final Element current = queue.poll();
            if (visited.contains(current.getNode())) {
                continue;
            }
            visited.add(current.getNode());
            System.out.println("current " + current);
            if (current.getNode().x() == endX && current.getNode().y() == endY
                    && (part1 || current.getNode().blocks() >= 4)) {
                return current.getHeatLoss();
            }

            queue.addAll(part1 ? current.getNeighbours(grid) : current.getNeighboursForPart2(grid));

        }

        return 0;
    }

    public static class Element implements Comparable<Element> {

        public static final int NORTH = 0;
        public static final int EAST = 1;
        public static final int SOUTH = 2;
        public static final int WEST = 3;

        private Node node;
        private int heatLoss;

        public Element(Node node, int heatLoss) {
            this.node = node;
            this.heatLoss = heatLoss;
        }

        public List<Element> getNeighboursForPart2(int[][] grid) {
            List<Element> neighbours = new ArrayList<>();
            if (node.blocks() >= 4) {
                Element left = getNextElement(Math.floorMod(node.direction() - 1, 4), grid, 1);
                if (left != null) {
                    neighbours.add(left);
                }

                Element right = getNextElement((node.direction() + 1) % 4, grid, 1);
                if (right != null) {
                    neighbours.add(right);
                }
            }
            if (node.blocks() < 10) {
                Element straight = getNextElement(node.direction(), grid, node.blocks() + 1);
                if (straight != null) {
                    neighbours.add(straight);
                }
            }
            return neighbours;
        }

        public List<Element> getNeighbours(int[][] grid) {
            List<Element> neighbours = new ArrayList<>();

            Element left = getNextElement(Math.floorMod(node.direction() - 1, 4), grid, 1);
            if (left != null) {
                neighbours.add(left);
            }

            Element right = getNextElement((node.direction() + 1) % 4, grid, 1);
            if (right != null) {
                neighbours.add(right);
            }

            if (node.blocks() < 3) {
                Element straight = getNextElement(node.direction(), grid, node.blocks() + 1);
                if (straight != null) {
                    neighbours.add(straight);
                }
            }

            //LOGGER.debug("neighbours {}: {}", node, neighbours);
            return neighbours;
        }

        private Element getNextElement(int direction, int[][] grid, int blocks) {
            int x = getNextX(direction);
            int y = getNextY(direction);
            if (x >= 0 && x < grid[0].length && y >= 0 && y < grid.length) {
                Node nextNode = new Node(x, y, blocks, direction);
                return new Element(nextNode, heatLoss + grid[nextNode.y()][nextNode.x()]);
            }
            return null;
        }

        private int getNextX(int newDirection) {
            int nextX = newDirection == NORTH || newDirection == SOUTH ? node.x()
                    : newDirection == EAST ? node.x() + 1 : node.x() - 1;
            return nextX;
        }

        private int getNextY(int newDirection) {
            int nextY = newDirection == EAST || newDirection == WEST ? node.y()
                    : newDirection == NORTH ? node.y() - 1 : node.y() + 1;
            return nextY;
        }

        @Override
        public int compareTo(Element o) {
            if (this.heatLoss != o.getHeatLoss()) {
                return Integer.compare(this.heatLoss, o.getHeatLoss());
            } else if (this.node.direction() == o.getNode().direction() && this.node.blocks() != o.getNode().blocks()) {
                return Integer.compare(this.node.blocks(), o.getNode().blocks());
            } else if (this.node.y() != o.getNode().y()) {
                return Integer.compare(this.node.y(), o.getNode().y());
            } else {
                return Integer.compare(this.node.x(), o.getNode().x());
            }
        }

        public Node getNode() {
            return this.node;
        }

        public int getHeatLoss() {
            return this.heatLoss;
        }

        @Override
        public String toString() {
            return "State{" +
                    "node=" + node +
                    ", heatLoss=" + heatLoss +
                    '}';
        }
    }


    public record Node(int x, int y, int blocks, int direction) {
    }

}
