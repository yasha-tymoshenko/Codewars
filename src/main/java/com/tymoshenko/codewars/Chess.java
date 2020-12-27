package com.tymoshenko.codewars;

import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 4 kyu
 * https://www.codewars.com/kata/549ee8b47111a81214000941/train/java
 */
public class Chess {

    private static final String LETTERS = "abcdefgh";
    private static final int BOARD_SIZE = 8;
    private static final int INFINITY = 100_000;
    private static final Map<Point, List<Point>> KNIGHT_MOVES_FOR_EACH_CHESSBOARD_SQUARE;
    private static final Pattern CHESS_COORDINATE_PATTERN = Pattern.compile("^[a-h][1-8]");

    private int[][] distances;
    private boolean[][] visited;
    private Map<Point, List<String>> pathMap;


    static {
        KNIGHT_MOVES_FOR_EACH_CHESSBOARD_SQUARE = initKnightMovesForEachChessboardSquare();
    }

    /**
     * Return the min number of steps for a chess knight to reach the finish square from the starting one.
     *
     * @param start  the coordinates of the knight's original position e.g. "a3".
     * @param finish the coordinates of the target square e.g. "h7".
     * @return the min number of steps to reach the destination
     */
    public static int knight(String start, String finish) {
        if (start == null || start.isBlank() || finish == null || finish.isBlank()) {
            throw new IllegalArgumentException("Knights target coordinates must not be empty.");
        }
        if (!CHESS_COORDINATE_PATTERN.matcher(start).matches()
                || !CHESS_COORDINATE_PATTERN.matcher(finish).matches()) {
            throw new IllegalArgumentException("Malformed chess coordinates.");
        }
        return new Chess().knightShortestRoute(start, finish);
    }

    private static Map<Point, List<Point>> initKnightMovesForEachChessboardSquare() {
        Map<Point, List<Point>> knightMovesMap = new HashMap<>();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                knightMovesMap.put(new Point(x, y), Stream.of(
                        new Point(x - 2, y - 1),
                        new Point(x - 1, y - 2),
                        new Point(x - 2, y + 1),
                        new Point(x - 1, y + 2),
                        new Point(x + 2, y - 1),
                        new Point(x + 1, y - 2),
                        new Point(x + 2, y + 1),
                        new Point(x + 1, y + 2)
                )
                        .filter(point -> point.x >= 0 && point.x < BOARD_SIZE
                                && point.y >= 0 && point.y < BOARD_SIZE)
                        .collect(Collectors.toList()));
            }
        }
        return knightMovesMap;
    }

    public int knightShortestRoute(String start, String finish) {
        Point begin = chessCoordinateToPoint(start);
        Point end = chessCoordinateToPoint(finish);
        initKnightsRoute();
        Queue<Point> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(point -> distances[point.x][point.y]));
        distances[begin.x][begin.y] = 0;
        priorityQueue.add(begin);
        while (!priorityQueue.isEmpty()) {
            Point nearestMove = priorityQueue.poll();
            if (visited[nearestMove.x][nearestMove.y]) {
                continue;
            }
            visited[nearestMove.x][nearestMove.y] = true;
            KNIGHT_MOVES_FOR_EACH_CHESSBOARD_SQUARE.get(nearestMove).forEach(followingMove -> {
                int distance = distances[nearestMove.x][nearestMove.y] + 1;
                if (distances[followingMove.x][followingMove.y] > distance) {
                    distances[followingMove.x][followingMove.y] = distance;
                    priorityQueue.add(followingMove);
                    addToPath(followingMove, nearestMove);
                }
            });
        }
        printPath(start, finish);
        return distances[end.x][end.y];
    }

    private void initKnightsRoute() {
        pathMap = new HashMap<>();
        distances = new int[BOARD_SIZE][BOARD_SIZE];
        visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            Arrays.fill(visited[i], false);
            Arrays.fill(distances[i], INFINITY);
        }
    }

    private Point chessCoordinateToPoint(String coordinates) {
        int x = LETTERS.indexOf(coordinates.charAt(0));
        // The numeration in array starts from 0, not from 1 as in chess board coordinates.
        int y = coordinates.charAt(1) - '0' - 1;
        return new Point(x, y);
    }

    private String pointToChessCoordinate(Point point) {
        return String.format("%s%d", LETTERS.charAt(point.x), (point.y + 1));
    }

    private void addToPath(Point move, Point previousMove) {
        List<String> previousPath = pathMap.getOrDefault(previousMove, Collections.emptyList());
        pathMap.computeIfAbsent(move, ignored -> new Stack<>()).add(pointToChessCoordinate(previousMove));
        pathMap.get(move).addAll(previousPath);
    }

    private void printPath(String start, String finish) {
        Point end = chessCoordinateToPoint(finish);
        List<String> knightMoves = pathMap.get(end);
        Collections.reverse(knightMoves);
        knightMoves.add(finish);
        System.out.printf("Shortest path from %s to %s (%d):\t%s.%n",
                start, finish, knightMoves.size() - 1, String.join(", ", knightMoves));
    }
}
