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

    private char[][] maze;
    private Map<Vertex, Queue<Vertex>> adjacentVertexesMap;

    /**
     * @param maze maze represented as String
     * @return the min number of steps to reach exit from the start or -1 if not reachable
     */
    public static int pathFinder(String maze) {
        PathFinder pathFinder = new PathFinder();

        pathFinder.parse(maze);
        out.println("solutions");
        Vertex begin = new Vertex(pathFinder.maze[0].length - 1, pathFinder.maze[0].length - 1);
        Vertex end = new Vertex(0, 0);
        return pathFinder.buildPath(begin, end, new LinkedList<>());
    }

    private void parse(String maze) {
        String[] mazeRows = maze.split("\n");
        int n = mazeRows.length;
        this.maze = new char[n][n];
        for (int i = 0; i < n; i++) {
            this.maze[i] = mazeRows[i].toCharArray();
        }

        this.adjacentVertexesMap = new LinkedHashMap<>();
        Vertex xy;
        out.println("Paths: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.maze[i][j] != 'W') {
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
    }

    private Vertex moveNorth(Vertex xy) {
        int nextX = xy.x - 1;
        return tryMoveX(nextX, xy.y);
    }

    private Vertex moveSouth(Vertex xy) {
        int nextX = xy.x + 1;
        return tryMoveX(nextX, xy.y);
    }

    private Vertex moveWest(Vertex xy) {
        int nextY = xy.y - 1;
        return tryMoveY(xy.x, nextY);
    }

    private Vertex moveEast(Vertex xy) {
        int nexyY = xy.y + 1;
        return tryMoveY(xy.x, nexyY);
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

    private Queue<Vertex> getNeighbours(Vertex xy) {
        return new LinkedList<>(adjacentVertexesMap.get(xy));
    }

    private int buildPath(Vertex start, Vertex end, List<Vertex> path) {
        Queue<Vertex> adj = getNeighbours(start);
        boolean found = adj.contains(end);
        path.add(start);
        if (found) {
            out.println("result");
            path.forEach(out::print);
            out.println("\n\n");

            return path.size();
        }
        while (!adj.isEmpty()) {
            Vertex nextXY = adj.poll();
            if (path.contains(nextXY)) {
                continue;
            }
            int ff = buildPath(nextXY, end, path);
            if (ff < 0) {
                path.remove(path.size() - 1);
            } else {
                return ff;
            }
        }
        return -1;
    }

    private Vertex tryMoveX(int nextX, int y) {
        if (nextX < 0 || nextX >= maze.length) {
            return Vertex.OUT_OF_BOUNDS;
        } else if (maze[nextX][y] == 'W') {
            return Vertex.WALL;
        } else {
            return new Vertex(nextX, y);
        }
    }

    private Vertex tryMoveY(int x, int nextY) {
        if (nextY < 0 || nextY >= maze.length) {
            return Vertex.OUT_OF_BOUNDS;
        } else if (maze[x][nextY] == 'W') {
            return Vertex.WALL;
        } else {
            return new Vertex(x, nextY);
        }
    }
}

class Vertex {
    static final Vertex OUT_OF_BOUNDS = new Vertex(-1, -1);
    static final Vertex WALL = new Vertex(-2, -2);

    int x;
    int y;

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

