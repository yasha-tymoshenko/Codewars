package com.tymoshenko.codewars.pathfinder;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * 4 kyu
 * <p>
 * https://www.codewars.com/kata/57658bfa28ed87ecfa00058a/train/java
 */
public class PathFinder {

    private final Map<Node, Queue<Node>> adjacencyMatrix;

    public PathFinder(String maze) {
        adjacencyMatrix = buildAdjacencyMatrix(maze);
    }

    public static int pathFinder(String maze) {
        return maze == null || maze.isBlank() ? -1 : new PathFinder(maze).findShortestPath();
    }

    private int findShortestPath() {
        Deque<Node> nodes = new LinkedList<>(adjacencyMatrix.keySet());
        if (nodes.isEmpty()) {
            return -1;
        }
        Node start = nodes.peekFirst();
        Node finish = nodes.peekLast();

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
        for (int x = 0; x < mazeRows.length; x++) {
            for (int y = 0; y < mazeRows.length; y++) {
                if (mazeRows[x].charAt(y) == 'W') {
                    continue;
                }
                Node xy = new Node(x, y);
                nodeMap.put(xy.toString(), xy);
            }
        }
        return nodeMap.values().stream()
                .collect(toMap(node -> node, node -> node.neighbourCoordinates()
                                .filter(nodeMap::containsKey)
                                .map(nodeMap::get)
                                .collect(toCollection(LinkedList::new)),
                        (a, b) -> b, TreeMap::new));
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
        return coordinate(x, y);
    }

    @Override
    public int compareTo(Node other) {
        int compare = Integer.compare(x, other.x);
        return compare == 0 ? Integer.compare(y, other.y) : compare;
    }

    public Stream<String> neighbourCoordinates() {
        return Stream.of(
                coordinate(x - 1, y),
                coordinate(x + 1, y),
                coordinate(x, y - 1),
                coordinate(x, y + 1)
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
                shortestPath.stream().map(Node::toString).collect(joining(", ")));
    }

    private String coordinate(int x, int y) {
        return String.format("(%2d, %2d)", x, y);
    }
}