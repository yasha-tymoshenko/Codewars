package com.tymoshenko.codewars;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

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
        pathFinder.parse(maze);
        out.println("solutions");
        Vertex begin =  new Vertex(pathFinder.n - 1, pathFinder.n - 1);
        Vertex end = new Vertex(0, 0);
        return pathFinder.dejkstra(begin, end, new LinkedList<>());
    }

    private void parse(String maze) {
        String[] mazeRows = maze.split("\n");
        n = mazeRows.length;
        char[][] mazze = new char[n][n];
        for (int i = 0; i < n; i++) {
            mazze[i] = mazeRows[i].toCharArray();
        }
        MazeParser parser = new MazeParser(mazze);
        this.adjacentVertexesMap = parser.parse();
    }

    private int dejkstra(Vertex start, Vertex end, List<Vertex> path) {
        start.distance = 0;
        path.add(start);

        adjacentVertexesMap.keySet().stream()
                .filter(v -> !v.equals(start))
                .forEach(v -> v.distance = Integer.MAX_VALUE);

        List<Vertex> visited = new LinkedList<>();
        List<Vertex> unvisited = new LinkedList<>();

        unvisited.add(end);

        // While exists unvisited
        while (!unvisited.isEmpty()) {
            Vertex nearest = getNearestNeighbour(unvisited);
            unvisited.remove(nearest);

            for (Vertex neighbour : getNeighbours(nearest)) {
                if (!visited.contains(neighbour)) {
                    if (neighbour.distance < nearest.distance + 1) {
                        neighbour.distance = nearest.distance + 1;
                        unvisited.add(neighbour);
                    }
                }
            }

            visited.add(nearest);
        }

        return visited.stream().filter(start::equals).findAny().orElse(Vertex.WALL).distance;
    }

    private Vertex getNearestNeighbour(List<Vertex> list) {
        Vertex next = list.get(0);
        for (Vertex vertex : list) {
            if (vertex.distance < next.distance) {
                next = vertex;
            }
        }
        return next;
    }

    private Queue<Vertex> getNeighbours(Vertex xy) {
        return new LinkedList<>(adjacentVertexesMap.get(xy));
    }
}

class MazeParser {
    private final char[][] maze;

    public MazeParser(char[][] maze) {
        this.maze = maze;
    }

    Map<Vertex, Queue<Vertex>> parse() {
        Map<Vertex, Queue<Vertex>> adjacentVertexesMap = new LinkedHashMap<>();
        Vertex xy;
        out.println("Paths: ");
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                if (maze[i][j] != 'W') {
                    xy = new Vertex(i, j);
                    Queue<Vertex> adj = adjacentCells(xy);
                    out.println("(" + i + "," + j + ")= " + adj.stream().filter(Objects::nonNull)
                            .map(Vertex::toString)
                            .collect(Collectors.joining("; "))
                    );
                    adjacentVertexesMap.put(xy, adj);
                }
            }
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
            return new Vertex(nextX, nextY);
        }
    }
}

class Vertex {
    static final Vertex OUT_OF_BOUNDS = new Vertex(-1, -1);
    static final Vertex WALL = new Vertex(-2, -2);

    static {
        WALL.distance = -1;
    }

    int x;
    int y;
    int distance;

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
}

