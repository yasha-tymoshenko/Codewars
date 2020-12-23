package com.tymoshenko.codewars;

import org.jetbrains.annotations.NotNull;

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
 */
public class PathFinder {

    private int n;
    private Map<Vertex, Queue<Vertex>> adjacentVertexesMap;

    /**
     * @param maze maze represented as String
     * @return the min number of steps to reach exit from the start or -1 if not reachable
     */
    public static int pathFinder(String maze) {
        PathFinder pathFinder = new PathFinder();
        String[] mazeRows = maze.split("\n");
        pathFinder.n = mazeRows.length;
        char[][] mazze = new char[pathFinder.n][pathFinder.n];
        for (int i = 0; i < pathFinder.n; i++) {
            mazze[i] = mazeRows[i].toCharArray();
        }
        MazeParser parser = new MazeParser(mazze);
        pathFinder.adjacentVertexesMap = parser.parse();
        Vertex begin = parser.vertexMap.get(String.format("%4d%4d", 0, 0));
        Vertex end = parser.vertexMap.get(String.format("%4d%4d", pathFinder.n -  1, pathFinder.n-1));
        return pathFinder.dejkstra(begin, end);
    }

    private int dejkstra(Vertex start, Vertex end) {
        start.distance = 0;

        showAdj();

        visitNeighbours(start);
        Queue<Vertex> unvisited = new LinkedList<>(getNeighbours(start));
        while (!unvisited.isEmpty()) {
            Vertex next = unvisited.poll();
            visitNeighbours(next);
            Queue<Vertex> neighbours = getNeighbours(next);
            unvisited.addAll(neighbours.stream().filter(neighbour -> !neighbour.visited).collect(Collectors.toList()));
        }

        System.out.println("\nNot visited:");
        adjacentVertexesMap.keySet().stream()
                .filter(v -> !v.visited)
                .forEach(v -> System.out.println(v + " " + v.distance));
        int distance = end.distance;
        return distance >= 10_000 ? -1 : distance;
    }

    private void showAdj() {
        System.out.println("Adjacent map:");
        adjacentVertexesMap.entrySet()
                .forEach(e -> System.out.println(e.getKey() + " " + e.getValue().stream().map(Vertex::toString).collect(Collectors.joining(","))));
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
        v.visited = true;
    }

    private Queue<Vertex> getNeighbours(Vertex xy) {
        return new LinkedList<>(adjacentVertexesMap.get(xy));
    }
}

class MazeParser {
    private final char[][] maze;
    Map<String, Vertex> vertexMap;

    public MazeParser(char[][] maze) {
        this.maze = maze;
    }

    Map<Vertex, Queue<Vertex>> parse() {
        vertexMap = new HashMap<>();
        Map<Vertex, Queue<Vertex>> adjacentVertexesMap = new TreeMap<>();
        Vertex xy;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                if (maze[i][j] != 'W') {
                    xy = new Vertex(i, j);
                    vertexMap.put(String.format("%4d%4d", i, j), xy);
                }
            }
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
        if (nextX < 0 || nextX >= maze.length || nextY < 0 || nextY >= maze.length) {
            return Vertex.OUT_OF_BOUNDS;
        } else if (maze[nextX][nextY] == 'W') {
            return Vertex.WALL;
        } else {
            return vertexMap.get(String.format("%4d%4d", nextX, nextY));
        }
    }
}

class Vertex implements Comparable<Vertex> {
    static final Vertex OUT_OF_BOUNDS = new Vertex(-1, -1);
    static final Vertex WALL = new Vertex(-2, -2);

    static {
        WALL.distance = -1;
        OUT_OF_BOUNDS.distance = Integer.MAX_VALUE;
    }

    int x;
    int y;
    int distance = 10_000;
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
    public int compareTo(@NotNull Vertex o) {
        int compare = Integer.compare(x, o.x);
        return compare == 0 ? Integer.compare(y, o.y) : compare;
    }
}

