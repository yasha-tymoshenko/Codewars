package com.tymoshenko.codewars;

import java.util.Arrays;

/**
 * 4 kyu
 * <p>
 * https://www.codewars.com/kata/5765870e190b1472ec0022a2/train/java
 */
public class PathFinderCanYouReachTheExit {

    private static final int INFINITY = 10_000_000;

    public static boolean pathFinder(String maze) {
        return maze != null && !maze.isBlank() && new PathFinderCanYouReachTheExit(maze).isExitReachable();
    }

    private int[][] board;

    public PathFinderCanYouReachTheExit(String maze) {
        initBoard(maze);
    }

    public boolean isExitReachable() {
        if (board.length == 1) {
            return true;
        }
        walk(0, 0, 0);
        int mazeExitValue = board[board.length - 1][board.length - 1];
        return mazeExitValue > 0 && mazeExitValue < INFINITY;
    }

    private void initBoard(String maze) {
        String[] mazeRows = maze.split("\n");
        int n = mazeRows.length;
        board = new int[n][];
        for (int x = 0; x < n; x++) {
            board[x] = new int[n];
            for (int y = 0; y < n; y++) {
                board[x][y] = mazeRows[x].charAt(y) == '.' ? INFINITY : -1;
            }
        }
    }

    private void walk(int x, int y, int distanceFromEntrance) {
        if (isOutOfBounds(x, y) || board[x][y] <= distanceFromEntrance
                || distanceFromEntrance == board.length * board.length) {
            return;
        }
        board[x][y] = distanceFromEntrance;
        if (x == board.length - 1 && y == board.length - 1) {
            return;
        }
        distanceFromEntrance++;
        walk(x, y - 1, distanceFromEntrance);
        walk(x, y + 1, distanceFromEntrance);
        walk(x - 1, y, distanceFromEntrance);
        walk(x + 1, y, distanceFromEntrance);
    }

    private boolean isOutOfBounds(int... xy) {
        return Arrays.stream(xy).anyMatch(c -> c < 0 || c >= board.length);
    }
}
