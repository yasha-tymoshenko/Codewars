package com.tymoshenko.codewars.pathfinder;

import java.awt.*;
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

    private Point[][][] adj;
    private int[][][] weights;

    public Finder(String maze) {
        initMaze(maze);
    }

    private int findShortestPath() {
        distances[0][0] = 0;
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze.length; y++) {
                int d = INFINITY;
                Point p = new Point(x, y);
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze.length; j++) {
                        if (visited[i][j]) {
                            continue;
                        }
                        if (distances[i][j] < d) {
                            d = distances[i][j];
                            p = new Point(i, j);
                        }
                    }
                }
                Point nearest = p;
                Point[] adjacent = adj[nearest.x][nearest.y];
                for (int i = 0; i < adjacent.length; i++) {
                    Point neighbour = adjacent[i];
                    int closestD = distances[nearest.x][nearest.y] + weights[nearest.x][nearest.y][i];
                    if (distances[neighbour.x][neighbour.y] > closestD) {
                        distances[neighbour.x][neighbour.y] = closestD;
                    }
                }
                visited[nearest.x][nearest.y] = true;
            }
        }
        return distances[maze.length - 1][maze.length - 1];
    }

    private void initMaze(String text) {
        String[] mazeRows = text.split("\n");
        maze = new int[mazeRows.length][];
        distances = new int[mazeRows.length][];
        visited = new boolean[mazeRows.length][];
        adj = new Point[mazeRows.length][][];
        weights = new int[mazeRows.length][][];
        for (int x = 0; x < mazeRows.length; x++) {
            maze[x] = new int[mazeRows.length];
            distances[x] = new int[mazeRows.length];
            visited[x] = new boolean[mazeRows.length];
            adj[x] = new Point[mazeRows.length][];
            for (int y = 0; y < mazeRows.length; y++) {
                maze[x][y] = mazeRows[x].charAt(y) - '0';
                distances[x][y] = INFINITY;
                visited[x][y] = false;
                adj[x][y] = Stream.of(
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
                weights[x][y] = new int[adj[x][y].length];
                for (int i = 0; i < adj[x][y].length; i++) {
                    weights[x][y][i] = Math.abs(maze[x][y] - maze[adj[x][y][i].x][adj[x][y][i].y]);
                }
            }
        }
    }
}