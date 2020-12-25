package com.tymoshenko.codewars.pathfinder;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Finder(String maze) {
        initMaze(maze);
    }

    private int findShortestPath() {
        distances[0][0] = 0;
        LinkedList<Point> unvisited = allCoordinates();
        while (!unvisited.isEmpty()) {
            Point nearest = nearest(unvisited);
            unvisited.remove(nearest);
            visitNeighbours(nearest);
        }
        return distances[maze.length - 1][maze.length - 1];
    }

    private LinkedList<Point> allCoordinates() {
        LinkedList<Point> unvisited = new LinkedList<>();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                unvisited.add(new Point(i, j));
            }
        }
        return unvisited;
    }

    private Point nearest(List<Point> unvisited) {
        Point nearest = unvisited.get(0);
        List<Point> neighbours = neighbours(nearest);
        int minD = distances[nearest.x][nearest.y];
        for (Point neighbour : neighbours) {
            if (minD > distances[neighbour.x][neighbour.y]) {
                minD = distances[neighbour.x][neighbour.y];
                nearest = neighbour;
            }
        }
        return nearest;
    }

    private void visitNeighbours(Point point) {
        visited[point.x][point.y] = true;
        neighbours(point).forEach(neighbour -> {
            int d = distances[point.x][point.y] + Math.abs(maze[point.x][point.y] - maze[neighbour.x][neighbour.y]);
            if (distances[neighbour.x][neighbour.y] > d) {
                distances[neighbour.x][neighbour.y] = d;
            }
        });
    }

    private List<Point> neighbours(Point p) {
        return Arrays.stream(new Point[]{
                new Point(p.x, p.y - 1),
                new Point(p.x, p.y + 1),
                new Point(p.x - 1, p.y),
                new Point(p.x + 1, p.y)
        })
                .filter(p1 -> p1.x >= 0 && p1.x < maze.length && p1.y >= 0 && p1.y < maze.length)
                .filter(p1 -> !visited[p1.x][p1.y])
                .collect(Collectors.toList());
    }

    private void initMaze(String text) {
        String[] mazeRows = text.split("\n");

        System.out.printf("%n%nMaze %d*%d", mazeRows.length, mazeRows.length);

        maze = new int[mazeRows.length][];
        distances = new int[mazeRows.length][];
        visited = new boolean[mazeRows.length][];
        for (int x = 0; x < mazeRows.length; x++) {
            maze[x] = new int[mazeRows.length];
            distances[x] = new int[mazeRows.length];
            visited[x] = new boolean[mazeRows.length];
            for (int y = 0; y < mazeRows.length; y++) {
                maze[x][y] = mazeRows[x].charAt(y);
                distances[x][y] = INFINITY;
                visited[x][y] = false;
            }
        }
    }
}