package com.tymoshenko.codewars.pathfinder;

import java.awt.*;
import java.util.ArrayList;

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

    private ArrayList<Point>[][] adj;
    private ArrayList<Integer>[][] weights;

    public Finder(String maze) {
        initMaze(maze);
    }

    private int findShortestPath() {
        distances[0][0] = 0;
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze.length; y++) {
                int d = INFINITY;
                Point p = new Point(x,y);
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
                ArrayList<Point> adjacent = adj[nearest.x][nearest.y];
                for (int i = 0; i < adjacent.size(); i++) {
                    Point neighbour = adjacent.get(i);
                    int closestD = distances[nearest.x][nearest.y] + weights[nearest.x][nearest.y].get(i);
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

        System.out.printf("%n%nMaze %d*%d%n%n", mazeRows.length, mazeRows.length);

        distances = new int[mazeRows.length][];
        visited = new boolean[mazeRows.length][];
        adj = new ArrayList[mazeRows.length][];
        weights = new ArrayList[mazeRows.length][];
        for (int x = 0; x < mazeRows.length; x++) {
            maze[x] = new int[mazeRows.length];
            distances[x] = new int[mazeRows.length];
            visited[x] = new boolean[mazeRows.length];
            adj[x] = new ArrayList[mazeRows.length];
            for (int y = 0; y < mazeRows.length; y++) {
                maze[x][y] = mazeRows[x].charAt(y) - '0';
                distances[x][y] = INFINITY;
                visited[x][y] = false;

                adj[x][y] = new ArrayList<>();
                Point a;
                if (x > 0) {
                    a = new Point(x - 1, y);
                    adj[x][y].add(a);
                }
                if (x < mazeRows.length - 1) {
                    a = new Point(x + 1, y);
                    adj[x][y].add(a);
                }
                if (y > 0) {
                    a = new Point(x, y - 1);
                    adj[x][y].add(a);
                }
                if (y < maze.length - 1) {
                    a = new Point(x, y + 1);
                    adj[x][y].add(a);
                }
            }
        }

        for (int x = 0; x < maze.length; x++) {
            weights[x] = new ArrayList[mazeRows.length];
            for (int y = 0; y < maze.length; y++) {
                weights[x][y] = new ArrayList<>();
                for (int i = 0; i < adj[x][y].size(); i++) {
                    weights[x][y].add(Math.abs(maze[x][y] - maze[adj[x][y].get(i).x][adj[x][y].get(i).y]));
                }
            }
        }
    }
}