package com.tymoshenko.codewars.battleship;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

class Ship {
    private final java.util.List<Point> cells;

    private Ship(Point cell) {
        cells = new ArrayList<>();
        cells.add(cell);
    }

    static Ship create(int[][] gameMap, int x, int y) {
        Point firstCell = new Point(x, y);
        Ship ship = new Ship(firstCell);
        int dy = firstCell.y + 1;
        while (dy < gameMap.length && gameMap[firstCell.x][dy] == 1) {
            ship.addCell(firstCell.x, dy);
            dy++;
        }
        int dx = firstCell.x + 1;
        while (dx < gameMap.length && gameMap[dx][firstCell.y] == 1) {
            ship.addCell(dx, firstCell.y);
            dx++;
        }
        return ship;
    }

    @Override
    public String toString() {
        return cells.stream()
                .map(cell -> String.format("(%d, %d)", cell.x, cell.y))
                .collect(joining(", "));
    }

    void addCell(int x, int y) {
        cells.add(new Point(x, y));
    }

    void markAsRead(int[][] field) {
        cells.forEach(cell -> field[cell.x][cell.y] = -1);
    }

    java.util.List<Point> borders() {
        List<Point> borders = new ArrayList<>();
        Point firstCell = cells.get(0);
        Point lastCell = cells.get(cells.size() - 1);
        boolean horizontal = firstCell.x == lastCell.x;
        int iMin = horizontal ? firstCell.y - 1 : firstCell.x - 1;
        int iMax = horizontal ? lastCell.y + 1 : lastCell.x + 1;
        int jMin = horizontal ? firstCell.x - 1 : firstCell.y - 1;
        int jMax = horizontal ? firstCell.x + 1 : firstCell.y + 1;
        for (int i = iMin; i <= iMax; i++) {
            for (int j = jMin; j <= jMax; j++) {
                Point cell = horizontal ? new Point(j, i) : new Point(i, j);
                if (isWithinMap(cell) && !cells.contains(cell)) {
                    borders.add(cell);
                }
            }
        }
        return borders;
    }

    int size() {
        return cells.size();
    }

    private boolean isWithinMap(Point cell) {
        return cell.x >= 0 && cell.x < BattleField.GAME_MAP_SIZE
                && cell.y >= 0 && cell.y < BattleField.GAME_MAP_SIZE;
    }
}
