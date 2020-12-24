package com.tymoshenko.codewars;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 4 kyu
 * https://www.codewars.com/kata/57658bfa28ed87ecfa00058a/train/java
 * <p>
 * You are at position [0, 0] in maze NxN and you can only move in one of the four cardinal directions
 * (i.e. North, East, South, West).
 * Return the minimal number of steps to exit position [N-1, N-1]
 * if it is possible to reach the exit from the starting position.
 * Otherwise, return false in JavaScript/Python and -1 in C++/C#/Java.
 * <p>
 * Empty positions are marked ..
 * Walls are marked W.
 * Start and exit positions are guaranteed to be empty in all test cases.
 * <p>
 *
 * TODO refactor:
 * 1. Make shorter.
 * 2. Remove nested classes.
 */
public class PathFinder {

    private Map<Vertex, Queue<Vertex>> adjacentVertexesMap;

    /**
     * @param maze maze represented as String
     * @return the min number of steps to reach exit from the start or -1 if not reachable
     */
    public static int pathFinder(String maze) {
        PathFinder pathFinder = new PathFinder();
        MazeParser parser = new MazeParser(maze);
        pathFinder.adjacentVertexesMap = parser.parse();
        Vertex start = parser.getVertex(0, 0);
        Vertex finish = parser.getVertex(parser.getMazeLength() - 1, parser.getMazeLength() - 1);
        return pathFinder.findShortestPath(start, finish);
    }

    private int findShortestPath(Vertex start, Vertex finish) {
        start.setDistance(0);
        visitNeighbours(start);
        Queue<Vertex> unvisited = getNeighbours(start);
        while (!unvisited.isEmpty()) {
            Vertex next = unvisited.poll();
            visitNeighbours(next);
            getNeighbours(next).stream()
                    .filter(neighbour -> !neighbour.isVisited() && !unvisited.contains(neighbour))
                    .forEach(unvisited::add);
        }
        if (finish.isVisited()) {
            finish.printPath();
            return finish.getDistance();
        }
        return -1;
    }

    private void visitNeighbours(Vertex v) {
        if (v.isVisited()) {
            return;
        }
        Queue<Vertex> neighbours = getNeighbours(v);
        while (!neighbours.isEmpty()) {
            Vertex neighbour = neighbours.poll();
            int distanceToNeighbour = v.getDistance() + 1;
            if (neighbour.getDistance() > distanceToNeighbour) {
                neighbour.setDistance(distanceToNeighbour);
                neighbour.addToPath(v);
            }
        }
        // Visited means the distance to all it's neighbours was calculated.
        v.setVisited(true);
    }

    private Queue<Vertex> getNeighbours(Vertex xy) {
        return new LinkedList<>(adjacentVertexesMap.get(xy));
    }
}

class MazeParser {
    private static final String FORMAT = "%10d%10d";

    private final int mazeLength;
    private final String[] mazeRows;
    private Map<String, Vertex> vertexMap;

    public MazeParser(String maze) {
        mazeRows = maze.split("\n");
        mazeLength = mazeRows.length;
    }

    public Map<Vertex, Queue<Vertex>> parse() {
        vertexMap = new HashMap<>();
        for (int x = 0; x < mazeLength; x++) {
            for (int y = 0; y < mazeLength; y++) {
                if (isWall(x, y)) {
                    continue;
                }
                vertexMap.put(String.format(FORMAT, x, y), new Vertex(x, y));
            }
        }
        return vertexMap.values().stream()
                .collect(Collectors.toMap(vertex -> vertex, this::adjacentCells, (a, b) -> b, TreeMap::new));
    }

    public int getMazeLength() {
        return mazeLength;
    }

    public Vertex getVertex(int x, int y) {
        return vertexMap.get(String.format(FORMAT, x, y));
    }

    private Queue<Vertex> adjacentCells(Vertex xy) {
        return Arrays.stream(new Vertex[]{moveNorth(xy), moveSouth(xy), moveWest(xy), moveEast(xy)})
                .filter(Vertex::isAccessible)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private Vertex moveNorth(Vertex xy) {
        return tryMove(xy.getX() - 1, xy.getY());
    }

    private Vertex moveSouth(Vertex xy) {
        return tryMove(xy.getX() + 1, xy.getY());
    }

    private Vertex moveWest(Vertex xy) {
        return tryMove(xy.getX(), xy.getY() - 1);
    }

    private Vertex moveEast(Vertex xy) {
        return tryMove(xy.getX(), xy.getY() + 1);
    }

    private Vertex tryMove(int x, int y) {
        if (isOutOfBounds(x, y)) {
            return Vertex.OUT_OF_BOUNDS;
        } else if (isWall(x, y)) {
            return Vertex.WALL;
        } else {
            return vertexMap.get(String.format(FORMAT, x, y));
        }
    }

    private boolean isOutOfBounds(int... coordinates) {
        return Arrays.stream(coordinates).anyMatch(x -> x < 0 || x >= mazeLength);
    }

    private boolean isWall(int x, int y) {
        return mazeRows[x].charAt(y) == 'W';
    }
}

class Vertex implements Comparable<Vertex> {
    static final int INFINITY = 10_000_000;
    static final Vertex WALL = new Vertex(-1, -1, -1);
    static final Vertex OUT_OF_BOUNDS = new Vertex(-2, -2, -2);

    private final int x;
    private final int y;
    private final List<Vertex> path;

    private int distance;
    private boolean visited;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        distance = INFINITY;
        visited = false;
        path = new ArrayList<>();
    }

    private Vertex(int x, int y, int distance) {
        this(x, y);
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return x == vertex.getX() && y == vertex.getY();
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
    public int compareTo(Vertex o) {
        int compare = Integer.compare(x, o.getX());
        return compare == 0 ? Integer.compare(y, o.getY()) : compare;
    }

    public void printPath() {
        List<Vertex> shortestPath = new ArrayList<>(this.path);
        Collections.reverse(shortestPath);
        shortestPath.add(this);
        System.out.printf("Shortest path(%3d): %s.%n", shortestPath.size() - 1,
                shortestPath.stream().map(Vertex::toString).collect(Collectors.joining(", ")));
    }

    public void addToPath(Vertex previous) {
        path.add(previous);
        path.addAll(previous.getPath());
    }

    public boolean isAccessible() {
        return !(this.equals(OUT_OF_BOUNDS) || this.equals(WALL));
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance() {
        return distance;
    }

    public boolean isVisited() {
        return visited;
    }

    public List<Vertex> getPath() {
        return path;
    }
}

