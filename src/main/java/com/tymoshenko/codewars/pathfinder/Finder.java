package com.tymoshenko.codewars.pathfinder;

import java.awt.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * 3 kyu
 * <p>
 * https://www.codewars.com/kata/576986639772456f6f00030c/train/java
 */
public class Finder {

    private static final int INFINITY = 10_000_000;

    public static int pathFinder(String maze) {
        return maze == null || maze.isBlank() || maze.length() == 1
                ? 0
                : new Finder(maze).findShortestPath();
    }

    private int[][] maze;
    private int[][] distances;
    private boolean[][] visited;
    private Point[][][] adjacents;
    private int[][][] weights;

    public Finder(String maze) {
        initMaze(maze);
    }

    private int findShortestPath() {
        distances[0][0] = 0;
        TreeMap<Integer, Queue<Point>> shortestDistanceMap = new TreeMap<>();
        shortestDistanceMap.computeIfAbsent(0, ignored -> new LinkedList<>()).add(new Point(0, 0));
        while (!shortestDistanceMap.isEmpty()) {
            Map.Entry<Integer, Queue<Point>> nearestEntry = shortestDistanceMap.firstEntry();
            Queue<Point> queue = nearestEntry.getValue();
            while (!queue.isEmpty()) {
                Point nearest = queue.poll();
                if (visited[nearest.x][nearest.y]) {
                    continue;
                }
                visited[nearest.x][nearest.y] = true;
                // For all adjacent to the nearest point, calculate the min distance from the starting point.
                Point[] adj = adjacents[nearest.x][nearest.y];
                for (int i = 0; i < adj.length; i++) {
                    Point neighbour = adj[i];
                    int distance = distances[nearest.x][nearest.y] + weights[nearest.x][nearest.y][i];
                    if (distances[neighbour.x][neighbour.y] > distance) {
                        distances[neighbour.x][neighbour.y] = distance;
                        shortestDistanceMap.computeIfAbsent(distance, ignored -> new LinkedList<>()).add(neighbour);
                    }
                }
            }
            shortestDistanceMap.remove(nearestEntry.getKey());
        }
        return distances[maze.length - 1][maze.length - 1];
    }

    private void initMaze(String text) {
        String[] mazeRows = text.split("\n");
        maze = new int[mazeRows.length][];
        distances = new int[mazeRows.length][];
        visited = new boolean[mazeRows.length][];
        adjacents = new Point[mazeRows.length][][];
        weights = new int[mazeRows.length][][];
        for (int x = 0; x < mazeRows.length; x++) {
            maze[x] = new int[mazeRows.length];
            distances[x] = new int[mazeRows.length];
            visited[x] = new boolean[mazeRows.length];
            adjacents[x] = new Point[mazeRows.length][];
            for (int y = 0; y < mazeRows.length; y++) {
                maze[x][y] = mazeRows[x].charAt(y) - '0';
                distances[x][y] = INFINITY;
                visited[x][y] = false;
                adjacents[x][y] = Stream.of(
                        new Point(x - 1, y),
                        new Point(x + 1, y),
                        new Point(x, y - 1),
                        new Point(x, y + 1)
                )
                        .filter(p -> (p.x >= 0 && p.x <= maze.length - 1) && (p.y >= 0 && p.y <= maze.length - 1))
                        .toArray(Point[]::new);
            }
        }

        for (int x = 0; x < maze.length; x++) {
            weights[x] = new int[mazeRows.length][];
            for (int y = 0; y < maze.length; y++) {
                Point[] adjacentPoints = adjacents[x][y];
                weights[x][y] = new int[adjacentPoints.length];
                for (int i = 0; i < adjacentPoints.length; i++) {
                    Point adjacent = adjacentPoints[i];
                    weights[x][y][i] = Math.abs(maze[x][y] - maze[adjacent.x][adjacent.y]);
                }
            }
        }
    }
}