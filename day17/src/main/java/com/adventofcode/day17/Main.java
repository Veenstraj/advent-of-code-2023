package com.adventofcode.day17;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private final HashMap<Position, Integer> maze = new LinkedHashMap<>();
    private int maxX = 0;
    private int maxY = 0;

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.readFile("com/adventofcode/day17/inputfile_full.txt");
        main.printMaze();
        LOGGER.info("Part-1, Shortest path, minimal costs: {}", main.mazeWalker(false));
        LOGGER.info("Part-2, Shortest path, minimal costs: {}", main.mazeWalker(true));
    }

    private int mazeWalker(boolean useUltra) {
        Set<Node> visited = new HashSet<>();
        PriorityQueue<State> unsettled = new PriorityQueue<>(Comparator.comparing(State::getHeatLoss));

        Position startPosition = new Position(0, 0);
        Node node1 = new Node(startPosition, Direction.EAST, 0);
        addNextWhenInBounds(unsettled, new State(node1, 0), node1.direction());
        Node node2 = new Node(startPosition, Direction.SOUTH, 0);
        addNextWhenInBounds(unsettled, new State(node2, 0), node2.direction());

        while (!unsettled.isEmpty()) {
            State currentState = unsettled.remove();
            Node currentNode = currentState.getNode();
            if (visited.contains(currentNode)) {
                continue;
            }
            visited.add(currentNode);
            //LOGGER.debug("currentState: {}", currentState);

            if (currentNode.pos().x() == maxX && currentNode.pos().y() == maxY) {
                printMazeAndShortestPath(currentState.getShortestPath());
                return currentState.getHeatLoss();
            }

            if (useUltra) {
                addNextUltra(unsettled, currentState);
            } else {
                addNext(unsettled, currentState);
            }
        }
        return -1; // no solution
    }

    private void addNext(Collection<State> stateCollection, State state) {
        Node node = state.getNode();
        if (node.numberOfSteps() < 3) {
            addNextWhenInBounds(stateCollection, state, node.direction());
        }
        addNextWhenInBounds(stateCollection, state, node.direction().turnClockwise());
        addNextWhenInBounds(stateCollection, state, node.direction().turnAntiClockwise());
    }

    private void addNextUltra(Collection<State> stateCollection, State state) {
        Node node = state.getNode();
        if (node.numberOfSteps() < 10) {
            addNextWhenInBounds(stateCollection, state, node.direction());
        }
        if (node.numberOfSteps() > 3) {
            addNextWhenInBounds(stateCollection, state, node.direction().turnClockwise());
            addNextWhenInBounds(stateCollection, state, node.direction().turnAntiClockwise());
        }
    }

    private void addNextWhenInBounds(Collection<State> stateCollection, State state, Direction direction) {
        Node node = state.getNode();
        Position nextPosition = node.pos().next(direction);
        if (maze.get(nextPosition) != null) { // a nextPosition exists
            int newCost = maze.get(nextPosition);
            int newNumberOfSteps = direction == node.direction() ? node.numberOfSteps() + 1 : 1;
            Node newNode = new Node(nextPosition, direction, newNumberOfSteps);

            LinkedList<Node> shortestPath = new LinkedList<>(state.getShortestPath());
            shortestPath.add(newNode);

            stateCollection.add(new State(newNode, state.getHeatLoss() + newCost, shortestPath));
        }
    }

    private void printMazeAndShortestPath(List<Node> shortestPath) {
        HashMap<Position, Direction> pdMap = new HashMap<>();
        if (shortestPath != null) {
            shortestPath.forEach(n -> pdMap.put(n.pos(), n.direction()));
        }
        int oldX = Integer.MAX_VALUE;
        for (Position pos : maze.keySet()) {
            if (pos.x() < oldX) {
                System.out.println();
            }
            oldX = pos.x();
            System.out.print(switch (pdMap.get(pos)) {
                case NORTH -> "^";
                case EAST -> ">";
                case SOUTH -> "v";
                case WEST -> "<";
                case null -> maze.get(pos);
            });
        }
        System.out.println();
    }

    private void printMaze() {
        printMazeAndShortestPath(null);
    }

    private void readFile(String filename) throws IOException, URISyntaxException {
        int y = 0;
        try (BufferedReader reader = Files.newBufferedReader(getFilePath(filename))) {
            String line = reader.readLine();
            maxX = line.length() - 1;
            while (line != null) {
                mapLine(y++, line);
                line = reader.readLine();
            }
        }
        maxY = y - 1;
    }

    private void mapLine(int y, String s) {
        char[] charArray = s.toCharArray();
        for (int x = 0; x < charArray.length; x++) {
            maze.put(new Position(x, y), Character.getNumericValue(charArray[x]));
        }
    }

    private Path getFilePath(String filename) throws URISyntaxException {
        return Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                        .getResource(filename), "file " + filename + " not found")
                .toURI());
    }

}
