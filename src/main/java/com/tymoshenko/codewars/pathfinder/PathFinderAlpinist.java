package com.tymoshenko.codewars.pathfinder;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * 3 kyu
 * <p>
 * https://www.codewars.com/kata/576986639772456f6f00030c/train/java
 */
public class PathFinderAlpinist {

    public static int pathFinder(String maze) {
        return maze == null || maze.isBlank() || maze.length() == 1
                ? 0
                : new PathFinderAlpinist(maze).findShortestPath();
    }

    private final Map<MountainNode, Queue<MountainNode>> adjacencyMatrix;
    private final Map<String, Integer> edgesWeightMap;

    public PathFinderAlpinist(String maze) {
//        System.out.println("\n" + maze + "\n");
        adjacencyMatrix = buildAdjacencyMatrix(maze);
        edgesWeightMap = new TreeMap<>();
        adjacencyMatrix.forEach((mountain, neighbours) -> neighbours
                .forEach(neighbour -> edgesWeightMap
                        .put(edge(mountain, neighbour), Math.abs(mountain.altitude - neighbour.altitude))));
    }

    private int findShortestPath() {
        Deque<MountainNode> nodes = new LinkedList<>(adjacencyMatrix.keySet());
        if (nodes.isEmpty()) {
            return 0;
        }
        MountainNode start = nodes.peekFirst();
        MountainNode finish = nodes.peekLast();
        LinkedList<MountainNode> unvisited = new LinkedList<>(nodes);

        start.distance = 0;
        while (!unvisited.isEmpty()) {
            MountainNode next = nearest(unvisited);
            if (next == null) {
                break;
            }
            visitNeighbours(next, unvisited);
        }

//        System.out.println("\nPath:");
//        System.out.print(start.altitude + "  ");
//        finish.path.forEach(m -> System.out.print(m.altitude + "  "));

        return finish.visited ? finish.distance : -1;
    }

    private MountainNode nearest(LinkedList<MountainNode> unvisited) {
        if (unvisited.isEmpty()) {
            return null;
        }
        MountainNode nearest = unvisited.getFirst();
        int minD = nearest.distance;
        for (int i = 1; i < unvisited.size(); i++) {
            if (minD > unvisited.get(i).distance) {
                nearest = unvisited.get(i);
                minD = nearest.distance;
            }
        }
        return nearest;
    }

    private void visitNeighbours(MountainNode mountain, LinkedList<MountainNode> unvisited) {
        Queue<MountainNode> neighbours = getNeighbours(mountain);
        while (!neighbours.isEmpty()) {
            MountainNode neighbour = neighbours.poll();
            int distanceToNeighbour = mountain.distance + edgesWeightMap.get(edge(mountain, neighbour));
            if (neighbour.distance > distanceToNeighbour) {
                neighbour.distance = distanceToNeighbour;
                neighbour.addToPath(mountain);
            }
        }
        // Visited means the distance to all it's neighbours was calculated.
        mountain.visited = true;
        unvisited.remove(mountain);
    }

    private Queue<MountainNode> getNeighbours(MountainNode xy) {
        return new LinkedList<>(adjacencyMatrix.get(xy));
    }

    private String edge(MountainNode mountain, MountainNode neighbour) {
        return String.format("%s->%s", mountain.toString(), neighbour.toString());
    }

    private Map<MountainNode, Queue<MountainNode>> buildAdjacencyMatrix(String maze) {
        String[] mazeRows = maze.split("\n");
        Map<String, MountainNode> nodeMap = new HashMap<>();
        for (int x = 0; x < mazeRows.length; x++) {
            for (int y = 0; y < mazeRows.length; y++) {
                MountainNode xy = new MountainNode(x, y, Integer.parseInt("" + mazeRows[x].charAt(y)));
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