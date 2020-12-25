package com.tymoshenko.codewars.pathfinder;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
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

    public Finder(String maze) {
        initMaze(maze);
    }

    private int findShortestPath() {
        distances[0][0] = 0;
        List<Point> unvisited = allCoordinates();
        while (!unvisited.isEmpty()) {
            Point nearest = nearest(unvisited);
            unvisited.remove(nearest);
            visited[nearest.x][nearest.y] = true;
            visitNeighbours(nearest.x, nearest.y);
        }
        return distances[maze.length - 1][maze.length - 1];
    }

    private List<Point> allCoordinates() {
        List<Point> unvisited = new LinkedList<>();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                unvisited.add(new Point(i, j));
            }
        }
        return unvisited;
    }

    private Point nearest(List<Point> unvisited) {
        Point nearest = unvisited.get(0);
        int minD = distances[nearest.x][nearest.y];
        for (int i = 1; i < unvisited.size(); i++) {
            Point next = unvisited.get(i);
            if (minD > distances[next.x][next.y]) {
                minD = distances[next.x][next.y];
                nearest = next;
            }
        }
        return nearest;
    }

    private void visitNeighbours(int x, int y) {
        Stream<Point> neighbours = Stream.of(
                new Point(x, y - 1),
                new Point(x, y + 1),
                new Point(x - 1, y),
                new Point(x + 1, y)
        );
        neighbours
                .filter(p -> p.x >= 0 && p.x < maze.length && p.y >= 0 && p.y < maze.length)
                .filter(p -> !visited[p.x][p.y])
                .forEach(neighbour -> {
                    int d = distances[x][y] + Math.abs(maze[x][y] - maze[neighbour.x][neighbour.y]);
                    if (distances[neighbour.x][neighbour.y] > d) {
                        distances[neighbour.x][neighbour.y] = d;
                    }
                });
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