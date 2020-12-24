package com.tymoshenko.codewars;

/**
 * 4 kyu
 * <p>
 * https://www.codewars.com/kata/5765870e190b1472ec0022a2/train/java
 */
public class PathFinderCanYouReachTheExit {

    private int[][] board;
    private boolean exitReached;

    public static boolean pathFinder(String maze) {
        return maze != null && !maze.isBlank() && new PathFinderCanYouReachTheExit(maze).isExitReachable();
    }

    public PathFinderCanYouReachTheExit(String maze) {
        initBoard(maze);
        exitReached = false;
    }

    public boolean isExitReachable() {
        if (board.length == 1) {
            return true;
        }
        walk(0, 0, 0);
        return board[board.length - 1][board.length - 1] > 0;
    }

    private void initBoard(String maze) {
        String[] mazeRows = maze.split("\n");
        int n = mazeRows.length;
        board = new int[n][];
        for (int x = 0; x < n; x++) {
            board[x] = new int[n];
            for (int y = 0; y < n; y++) {
                board[x][y] = mazeRows[x].charAt(y) == '.' ? 0 : -1;
            }
        }
    }

    private void walk(int x, int y, int distance) {
        if (exitReached || x < 0 || x >= board.length || y < 0 || y >= board.length || board[x][y] != 0) {
            return;
        }
        if (x == 0 && y == 0 && distance > 0) {
            return;
        }
        board[x][y] = distance;
        if (x == board.length - 1 && y == board.length - 1) {
            exitReached = true;
            return;
        }
        distance++;
        walk(x + 1, y, distance);
        walk(x, y + 1, distance);
        walk(x - 1, y, distance);
        walk(x, y - 1, distance);
    }
}
