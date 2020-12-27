package com.tymoshenko.codewars.pathfinder;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * 3 kyu
 * <p>
 * https://www.codewars.com/kata/576986639772456f6f00030c/train/java
 *
 * For the real solution, see Finder class in this package.
 * @see Finder
 */
public class PathFinderAlpinist {

    public static int pathFinder(String maze) {
        return maze == null || maze.isBlank() || maze.length() == 1
                ? 0
                : new PathFinderAlpinist(maze).findShortestPath();
    }

    private Map<MountainNode, Queue<MountainNode>> adjacencyMap;
    private Map<String, Integer> weightsMap;

    public PathFinderAlpinist(String maze) {
//        System.out.println("\n" + maze + "\n");
        initGraph(maze);
    }

    private int findShortestPath() {
        Deque<MountainNode> nodes = new LinkedList<>(adjacencyMap.keySet());
        if (nodes.isEmpty()) {
            return 0;
        }
        MountainNode start = nodes.peekFirst();
        MountainNode finish = nodes.peekLast();
        LinkedList<MountainNode> unvisited = new LinkedList<>(nodes);

        start.distance = 0;
        while (!unvisited.isEmpty()) {
            MountainNode next = nearest(unvisited);
            unvisited.remove(next);
            visitNeighbours(next);
        }

//        System.out.println("\nPath:");
//        System.out.print(start.altitude + "  ");
//        finish.path.forEach(m -> System.out.print(m.altitude + "  "));

        return finish.visited ? finish.distance : -1;
    }

    private MountainNode nearest(LinkedList<MountainNode> unvisited) {
        MountainNode nearest = unvisited.getFirst();
        for (int i = 1; i < unvisited.size(); i++) {
            if (nearest.distance > unvisited.get(i).distance) {
                nearest = unvisited.get(i);
            }
        }
        return nearest;
    }

    private void visitNeighbours(MountainNode node) {
        Queue<MountainNode> neighbours = getNeighbours(node);
        while (!neighbours.isEmpty()) {
            MountainNode neighbour = neighbours.poll();
            if (neighbour.visited) {
                continue;
            }
            int distance = node.distance + weightsMap.get(edge(node, neighbour));
            if (neighbour.distance > distance) {
                neighbour.distance = distance;
                neighbour.addToPath(node);
            }
        }
        // Visited means the distance to all it's neighbours was calculated.
        node.visited = true;
    }

    private Queue<MountainNode> getNeighbours(MountainNode node) {
        return new LinkedList<>(adjacencyMap.get(node));
    }

    private String edge(MountainNode node, MountainNode neighbour) {
        return String.format("%s->%s", node.toString(), neighbour.toString());
    }

    private void initGraph(String maze) {
        String[] mazeRows = maze.split("\n");
        Map<String, MountainNode> nodeMap = new HashMap<>();
        for (int x = 0; x < mazeRows.length; x++) {
            for (int y = 0; y < mazeRows.length; y++) {
                MountainNode xy = new MountainNode(x, y, Integer.parseInt("" + mazeRows[x].charAt(y)));
                nodeMap.put(xy.toString(), xy);
            }
        }
        adjacencyMap = new TreeMap<>();
        weightsMap = new TreeMap<>();
        for (MountainNode node : nodeMap.values()) {
            Queue<MountainNode> neighbours = node.neighbourCoordinates()
                    .filter(nodeMap::containsKey)
                    .map(nodeMap::get)
                    .collect(toCollection(LinkedList::new));
            adjacencyMap.put(node, neighbours);
            neighbours.forEach(neighbour ->
                    weightsMap.put(edge(node, neighbour), Math.abs(node.altitude - neighbour.altitude)));
        }
    }
}

class MountainNode implements Comparable<MountainNode> {
    static final int INFINITY = 10_000_000;

    final int x;
    final int y;
    final int altitude;
    List<MountainNode> path;

    int distance;
    boolean visited;

    public MountainNode(int x, int y, int altitude) {
        this.x = x;
        this.y = y;
        this.altitude = altitude;
        visited = false;
        distance = INFINITY;
        path = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MountainNode node = (MountainNode) o;
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
    public int compareTo(MountainNode other) {
        int compare = Integer.compare(x, other.x);
        return compare == 0 ? Integer.compare(y, other.y) : compare;
    }

    public Stream<String> neighbourCoordinates() {
        return Stream.of(
                coordinate(x, y - 1),
                coordinate(x, y + 1),
                coordinate(x - 1, y),
                coordinate(x + 1, y)
        );
    }

    public void addToPath(MountainNode previous) {
        path = new ArrayList<>(previous.path);
        path.add(this);
    }

    private String coordinate(int x, int y) {
        return String.format("(%2d, %2d)", x, y);
    }
}