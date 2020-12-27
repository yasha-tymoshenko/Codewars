package com.tymoshenko.codewars;

import java.awt.*;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

/**
 * 4 kyu
 * https://www.codewars.com/kata/549ee8b47111a81214000941/train/java
 */
public class Chess {

    private static final String LETTERS = "abcdefgh";
    private static final int BOARD_SIZE = 8;
    private static final int INFINITY = 100_000;

    private static Map<Point, Queue<Point>> reachableSquaresMap;

    static {
        initKnightMoves();
    }

    /**
     * Return the min number of steps for a chess knight to reach the finish square from the starting one.
     * @param start the coordinates of the knight's original position e.g. "a3".
     * @param finish the coordinates of the target square e.g. "h7".
     * @return the min number of steps to reach the destination
     */
    public static int knight(String start, String  finish) {
        return  new Chess().knightShortestRoute(start, finish);
    }

    private int[][] distances;
    private boolean[][] visited;

    public int knightShortestRoute(String start, String finish) {
        Point begin = parseSquareCoordinates(start);
        Point end = parseSquareCoordinates(finish);
        initKnightsRouteFromStartingPoint();
        distances[begin.x][begin.y] = 0;
        NavigableMap<Integer, Queue<Point>> priorityQueue = new TreeMap<>();
        priorityQueue.computeIfAbsent(0, integer -> new LinkedList<>()).add(begin);
        while (!priorityQueue.isEmpty()) {
            Map.Entry<Integer, Queue<Point>> firstEntry = priorityQueue.firstEntry();
            Queue<Point> sameDistanceMoves = firstEntry.getValue();
            while (!sameDistanceMoves.isEmpty()) {
                Point nearestMove = sameDistanceMoves.poll();
                if (visited[nearestMove.x][nearestMove.y]) {
                    continue;
                }
                visited[nearestMove.x][nearestMove.y] = true;
                Queue<Point> reachableSquares = reachableSquaresMap.get(nearestMove);
                for (Point reachableSquare : reachableSquares) {
                    int distance = distances[nearestMove.x][nearestMove.y] + 1;
                    if (distances[reachableSquare.x][reachableSquare.y] > distance) {
                        distances[reachableSquare.x][reachableSquare.y] = distance;
                        priorityQueue.computeIfAbsent(distance, ignored -> new LinkedList<>()).add(reachableSquare);
                    }
                }
            }
            priorityQueue.remove(firstEntry.getKey());
        }
        return distances[end.x][end.y];
    }

    private Point parseSquareCoordinates(String coordinates) {
        int x = LETTERS.indexOf(coordinates.charAt(0));
        // The numeration in array starts from 0, not from 1 as in chess board coordinates.
        int y = coordinates.charAt(1) - '0' - 1;
        return new Point(x, y);
    }

    private void initKnightsRouteFromStartingPoint() {
        distances = new int[BOARD_SIZE][];
        visited = new boolean[BOARD_SIZE][];
        for (int x = 0; x < BOARD_SIZE; x++) {
            distances[x] = new int[BOARD_SIZE];
            visited[x] = new boolean[BOARD_SIZE];
            for (int y = 0; y < BOARD_SIZE; y++) {
                distances[x][y] = INFINITY;
                visited[x][y] = false;
            }
        }
    }

    private static void initKnightMoves() {
        reachableSquaresMap = new TreeMap<>(new PointComparator());
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Point point = new Point(x, y);
                reachableSquaresMap.put(point, knightMoves(point));
            }
        }
    }

    private static Queue<Point> knightMoves(Point start) {
        int x = start.x;
        int y = start.y;
        return Stream.of(
                new Point(x - 2, y - 1),
                new Point(x - 1, y - 2),
                new Point(x - 2, y + 1),
                new Point(x - 1, y + 2),
                new Point(x + 2, y - 1),
                new Point(x + 1, y - 2),
                new Point(x + 2, y + 1),
                new Point(x + 1, y + 2)
        )
                .filter(point -> point.x >= 0 && point.x < BOARD_SIZE && point.y >= 0 && point.y < BOARD_SIZE)
                .collect(toCollection(LinkedList::new));
    }

    private static class PointComparator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            int compareX = Integer.compare(o1.x, o2.x);
            return compareX == 0 ? Integer.compare(o1.y, o2.y) : compareX;
        }
    }

}
