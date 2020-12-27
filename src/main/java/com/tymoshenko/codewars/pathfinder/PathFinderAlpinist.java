package com.tymoshenko.codewars.pathfinder;

import java.awt.Point;
import java.util.*;
import java.util.stream.Stream;

/**
 * 3 kyu
 * <p>
 * https://www.codewars.com/kata/576986639772456f6f00030c/train/java
 */
public class PathFinderAlpinist {

    private static final int INFINITY = 10_000_000;

    public static int pathFinder(String maze) {
        if (maze == null || maze.isBlank() || maze.trim().length() == 1) return 0;
        return new PathFinderAlpinist(maze).findShortestPath();
    }

    private int n;
    private int[][] distances;
    private boolean[][] visited;
    private Point[][][] adjacents;
    private int[][][] weights;

    public PathFinderAlpinist(String maze) {
        initMaze(maze);
    }

    public int findShortestPath() {
        distances[0][0] = 0;
        Queue<Point> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(point -> distances[point.x][point.y]));
        priorityQueue.add(new Point(0, 0));
        while (!priorityQueue.isEmpty()) {
            Point nearest = priorityQueue.poll();
            if (visited[nearest.x][nearest.y]) {
                continue;
            }
            visited[nearest.x][nearest.y] = true;
            Point[] adjacentPoints = adjacents[nearest.x][nearest.y];
            for (int i = 0; i < adjacentPoints.length; i++) {
                Point neighbour = adjacentPoints[i];
                int distance = distances[nearest.x][nearest.y] + weights[nearest.x][nearest.y][i];
                if (distances[neighbour.x][neighbour.y] > distance) {
                    distances[neighbour.x][neighbour.y] = distance;
                    priorityQueue.add(neighbour);
                }
            }
        }
        return distances[n - 1][n - 1];
    }

    private void initMaze(String mazeString) {
        String[] mazeRows = mazeString.split("\n");
        n = mazeRows.length;
        int[][] maze = new int[n][n];
        distances = new int[n][n];
        visited = new boolean[n][n];
        adjacents = new Point[n][n][];
        weights = new int[n][n][];
        for (int x = 0; x < n; x++) {
            Arrays.fill(distances[x], INFINITY);
            Arrays.fill(visited[x], false);
            for (int y = 0; y < n; y++) {
                maze[x][y] = mazeRows[x].charAt(y) - '0';
                adjacents[x][y] = Stream.of(new Point(x - 1, y), new Point(x + 1, y),
                        new Point(x, y - 1), new Point(x, y + 1))
                        .filter(point -> (point.x >= 0 && point.x <= n - 1) && (point.y >= 0 && point.y <= n - 1))
                        .toArray(Point[]::new);
            }
        }
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                int mountainHeight = maze[x][y];
                weights[x][y] = Arrays.stream(adjacents[x][y])
                        .mapToInt(adj -> Math.abs(mountainHeight - maze[adj.x][adj.y]))
                        .toArray();
            }
        }
    }
}