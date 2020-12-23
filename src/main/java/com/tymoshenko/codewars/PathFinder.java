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
 */
public class PathFinder {

    private char[][] maze;
    private Map<int[], Queue<int[]>> adjacentCellsMap;

    /**
     * @param maze maze represented as String
     * @return the min number of steps to reach exit from the start or -1 if not reachable
     */
    public static int pathFinder(String maze) {
        PathFinder pathFinder = new PathFinder();

        pathFinder.parse(maze);
        System.out.println("solutions");
        return pathFinder.buildPath(new int[] {pathFinder.maze[0].length -1, pathFinder.maze[0].length -1},new int[] {0,0}, new LinkedList<>());
    }

    private void parse(String maze) {
        String[] mazeRows = maze.split("\n");
        int n = mazeRows.length;
        this.maze = new char[n][n];
        for (int i = 0; i < n; i++) {
            this.maze[i] = mazeRows[i].toCharArray();
        }
        this.maze[0][0] = 'B';
        this.maze[n - 1][n - 1] = 'E';
        this.adjacentCellsMap = new LinkedHashMap<>();
        int[] xy;
        System.out.println("Paths: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.maze[i][j] != 'W') {
                    xy = new int[] {i,j};
                    Queue<int[]> adj = adjacentCells(xy);
                    System.out.println("(" + i + "," + j + ")= " + adj.stream().filter(Objects::nonNull)
                            .map(a -> "(" + a[0] + ", " + a[1] + ")")
                            .collect(Collectors.joining("; "))
                    );
                    adjacentCellsMap.put(xy, adj);
                }
            }
        }
    }

    private int[] moveNorth(int[] xy) {
        int nextX = xy[0] - 1;
        return tryMoveX(nextX, xy[1]);
    }

    private int[] moveSouth(int[] xy) {
        int nextX = xy[0] + 1;
        return tryMoveX(nextX, xy[1]);
    }

    private int[] moveWest(int[] xy) {
        int nextY = xy[1] - 1;
        return tryMoveY(xy[0], nextY);
    }

    private int[] moveEast(int[] xy) {
        int nexyY = xy[1] + 1;
        return tryMoveY(xy[0], nexyY);
    }

    private boolean isPassable(int[] nextXY) {
        return Arrays.stream(nextXY).noneMatch(c -> c < 0);
    }

    private Queue<int[]> adjacentCells(int[] xy) {
        Queue<int[]> adj = new LinkedList<>();
        int[] nextXY = moveNorth(xy);

        if (isPassable(nextXY)) {
            adj.add(nextXY);
        }
        nextXY = moveSouth(xy);
        if (isPassable(nextXY)) {
            adj.add(nextXY);
        }
        nextXY = moveWest(xy);
        if (isPassable(nextXY)) {
            adj.add(nextXY);
        }
        nextXY = moveEast(xy);
        if (isPassable(nextXY)) {
            adj.add(nextXY);
        }
        return adj;
    }

    private Queue<int[]> getNeighbours(int[] xy) {
        for (int[] ints : adjacentCellsMap.keySet()) {
            if (Arrays.equals(xy, ints)) return new LinkedList<>(adjacentCellsMap.get(ints));
        }
        return new LinkedList<>();
    }
    private int buildPath(int[] start, int[] end, List<int[]> path) {
        Queue<int[]> adj = getNeighbours(start);
        boolean found = adj.stream().anyMatch(xy -> Arrays.equals(xy, end));
        path.add(start);
        if (found) {
            System.out.println("result");
            path.forEach(xy -> System.out.print(Arrays.toString(xy)));
            System.out.println("\n\n");

            return path.size();
        }
        while (!adj.isEmpty()) {
            int[] nextXY = adj.poll();
            if (path.stream().anyMatch(xy -> Arrays.equals(xy, nextXY))) {
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

    private int[] tryMoveX(int nextX, int y) {
        if (nextX < 0 || nextX >= maze.length) {
            // out of bounds
            return new int[]{-1, y};
        } else if (maze[nextX][y] == 'W') {
            return new int[]{-2, y};
        } else {
            return new int[]{nextX, y};
        }
    }

    private int[] tryMoveY(int x, int nextY) {
        if (nextY < 0 || nextY >= maze.length) {
            // out of bounds
            return new int[]{x, -1};
        } else if (maze[x][nextY] == 'W') {
            return new int[]{x, -2};
        } else {
            return new int[]{x, nextY};
        }
    }

}

