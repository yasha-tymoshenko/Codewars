package com.tymoshenko.codewars;

import java.util.*;

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
 *
 * TODO refactor:
 * 1. Make shorter.
 * 2. Remove nested classes.
 * 3. Show the actual path, not just calculate it's distance.
 */
public class PathFinder {

    public static final String FORMAT = "%4d%4d";
    private Map<Vertex, Queue<Vertex>> adjacentVertexesMap;

    /**
     * @param maze maze represented as String
     * @return the min number of steps to reach exit from the start or -1 if not reachable
     */
    public static int pathFinder(String maze) {
        PathFinder pathFinder = new PathFinder();
        MazeParser parser = new MazeParser(maze);
        pathFinder.adjacentVertexesMap = parser.parse();
        int mazeLength = parser.mazeLength;
        Vertex begin = parser.vertexMap.get(String.format(FORMAT, 0, 0));
        Vertex end = parser.vertexMap.get(String.format(FORMAT, mazeLength -  1, mazeLength-1));
        return pathFinder.dejkstra(begin, end);
    }

    private int dejkstra(Vertex start, Vertex end) {
        start.distance = 0;
        visitNeighbours(start);
        Queue<Vertex> unvisited = getNeighbours(start);
        while (!unvisited.isEmpty()) {
            Vertex next = unvisited.poll();
            visitNeighbours(next);
            Queue<Vertex> neighbours = getNeighbours(next);
            for (Vertex neighbour : neighbours) {
                if (!neighbour.visited && !unvisited.contains(neighbour)) {
                    unvisited.add(neighbour);
                }
            }
        }
        return end.visited ? end.distance : -1;
    }

    private void visitNeighbours(Vertex v) {
        if (v.visited) {
            return;
        }
        Queue<Vertex> neighbours = getNeighbours(v);
        while (!neighbours.isEmpty()) {
            Vertex neighbour = neighbours.poll();
            int distanceToNeighbour = v.distance + 1;
            if (neighbour.distance > distanceToNeighbour) {
                neighbour.distance = distanceToNeighbour;
            }
        }
        // Visited means the distance to all it's neighbours was calculated.
        v.visited = true;
    }

    private Queue<Vertex> getNeighbours(Vertex xy) {
        return new LinkedList<>(adjacentVertexesMap.get(xy));
    }
}

class MazeParser {
    final int mazeLength;
    Map<String, Vertex> vertexMap;
    private final String[] mazeRows;

    public MazeParser(String maze) {
        mazeRows = maze.split("\n");
        mazeLength = mazeRows.length;
    }

    Map<Vertex, Queue<Vertex>> parse() {
        vertexMap = new HashMap<>();
        Map<Vertex, Queue<Vertex>> adjacentVertexesMap = new TreeMap<>();
        Vertex xy;
        int rowIndex = 0;
        for (String row : mazeRows) {
            for (int j = 0; j < mazeLength; j++) {
                if (row.charAt(j) != 'W') {
                    xy = new Vertex(rowIndex, j);
                    vertexMap.put(String.format(PathFinder.FORMAT, rowIndex, j), xy);
                }
            }
            rowIndex++;
        }

        for (Vertex vertex : vertexMap.values()) {
            Queue<Vertex> adj = adjacentCells(vertex);
            adjacentVertexesMap.put(vertex, adj);
        }
        return adjacentVertexesMap;
    }

    private Queue<Vertex> adjacentCells(Vertex xy) {
        Queue<Vertex> adj = new LinkedList<>();
        Vertex nextXY = moveNorth(xy);

        if (nextXY.isPassable()) {
            adj.add(nextXY);
        }
        nextXY = moveSouth(xy);
        if (nextXY.isPassable()) {
            adj.add(nextXY);
        }
        nextXY = moveWest(xy);
        if (nextXY.isPassable()) {
            adj.add(nextXY);
        }
        nextXY = moveEast(xy);
        if (nextXY.isPassable()) {
            adj.add(nextXY);
        }
        return adj;
    }

    private Vertex moveNorth(Vertex xy) {
        return tryMove(xy.x - 1, xy.y);
    }

    private Vertex moveSouth(Vertex xy) {
        return tryMove(xy.x + 1, xy.y);
    }

    private Vertex moveWest(Vertex xy) {
        return tryMove(xy.x, xy.y - 1);
    }

    private Vertex moveEast(Vertex xy) {
        return tryMove(xy.x, xy.y + 1);
    }

    private Vertex tryMove(int nextX, int nextY) {
        if (nextX < 0 || nextX >= mazeLength || nextY < 0 || nextY >= mazeLength) {
            return Vertex.OUT_OF_BOUNDS;
        } else if (mazeRows[nextX].charAt(nextY) == 'W') {
            return Vertex.WALL;
        } else {
            return vertexMap.get(String.format(PathFinder.FORMAT, nextX, nextY));
        }
    }
}

class Vertex implements Comparable<Vertex> {
    public static final int INFINITY = 10_00_000;
    static final Vertex OUT_OF_BOUNDS = new Vertex(-1, -1);
    static final Vertex WALL = new Vertex(-2, -2);

    static {
        WALL.distance = -1;
        OUT_OF_BOUNDS.distance = Integer.MAX_VALUE;
    }

    int x;
    int y;
    int distance = INFINITY;
    boolean visited = false;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isPassable() {
        return !(this.equals(OUT_OF_BOUNDS) || this.equals(WALL));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return x == vertex.x && y == vertex.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }

    @Override
    public int compareTo(Vertex o) {
        int compare = Integer.compare(x, o.x);
        return compare == 0 ? Integer.compare(y, o.y) : compare;
    }
}

