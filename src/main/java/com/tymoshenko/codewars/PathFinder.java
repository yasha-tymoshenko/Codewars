package com.tymoshenko.codewars;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 4 kyu
 *
 * https://www.codewars.com/kata/57658bfa28ed87ecfa00058a/train/java
 */
public class PathFinder {

    static final String FORMAT = "%10d%10d";

    private final Map<Node, Queue<Node>> adjacencyMatrix;

    public PathFinder(String maze) {
        adjacencyMatrix = buildAdjacencyMatrix(maze);
    }

    public static int pathFinder(String maze) {
        PathFinder pathFinder = new PathFinder(maze);
        return pathFinder.findShortestPath();
    }

    private int findShortestPath() {
        List<Node> nodes = new ArrayList<>(adjacencyMatrix.keySet());
        Node start = nodes.get(0);
        Node finish = nodes.get(nodes.size() - 1);

        start.distance = 0;
        visitNeighbours(start);
        Queue<Node> unvisited = getNeighbours(start);
        while (!unvisited.isEmpty()) {
            Node next = unvisited.poll();
            visitNeighbours(next);
            getNeighbours(next).stream()
                    .filter(neighbour -> !neighbour.visited && !unvisited.contains(neighbour))
                    .forEach(unvisited::add);
        }
        if (finish.visited) {
            finish.printPath();
            return finish.distance;
        }
        return -1;
    }

    private void visitNeighbours(Node v) {
        Queue<Node> neighbours = getNeighbours(v);
        while (!neighbours.isEmpty()) {
            Node neighbour = neighbours.poll();
            int distanceToNeighbour = v.distance + 1;
            if (neighbour.distance > distanceToNeighbour) {
                neighbour.distance = distanceToNeighbour;
                neighbour.addToPath(v);
            }
        }
        // Visited means the distance to all it's neighbours was calculated.
        v.visited = true;
    }

    private Queue<Node> getNeighbours(Node xy) {
        return new LinkedList<>(adjacencyMatrix.get(xy));
    }

    private Map<Node, Queue<Node>> buildAdjacencyMatrix(String maze) {
        String[] mazeRows = maze.split("\n");
        Map<String, Node> nodeMap = new HashMap<>();
        Map<Node, Queue<Node>> matrix = new TreeMap<>();
        for (int x = 0; x < mazeRows.length; x++) {
            for (int y = 0; y < mazeRows.length; y++) {
                if (mazeRows[x].charAt(y) == 'W') {
                    continue;
                }
                Node xy = new Node(x, y);
                nodeMap.put(String.format(FORMAT, x, y), xy);
            }
        }
        for (Node v : nodeMap.values()) {
            Queue<Node> adjacent = new LinkedList<>();
            v.neighbourCoordinates()
                    .filter(nodeMap::containsKey)
                    .forEach(key -> adjacent.add(nodeMap.get(key)));
            matrix.put(v, adjacent);
        }
        return matrix;
    }
}

class Node implements Comparable<Node> {
    static final int INFINITY = 10_000_000;

    final int x;
    final int y;
    final List<Node> path;

    int distance;
    boolean visited;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        visited = false;
        distance = INFINITY;
        path = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%2d, %2d)", x, y);
    }

    @Override
    public int compareTo(Node other) {
        int compare = Integer.compare(x, other.x);
        return compare == 0 ? Integer.compare(y, other.y) : compare;
    }

    public Stream<String> neighbourCoordinates() {
        return Stream.of(
                String.format(PathFinder.FORMAT, x - 1, y),
                String.format(PathFinder.FORMAT, x + 1, y),
                String.format(PathFinder.FORMAT, x, y - 1),
                String.format(PathFinder.FORMAT, x, y + 1)
        );
    }

    public void addToPath(Node previous) {
        path.add(previous);
        path.addAll(previous.path);
    }

    public void printPath() {
        List<Node> shortestPath = new ArrayList<>(this.path);
        Collections.reverse(shortestPath);
        shortestPath.add(this);
        System.out.printf("Shortest path(%3d): %s.%n", shortestPath.size() - 1,
                shortestPath.stream().map(Node::toString).collect(Collectors.joining(", ")));
    }
}