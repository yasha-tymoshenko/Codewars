package com.tymoshenko.codewars;


import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

/**
 * 3 kyu
 * <p>
 * Battleship field validator
 * <p>
 * https://www.codewars.com/kata/52bb6539a4cf1b12d90005b7/train/java
 */
public class BattleField {

    static final int BOARD_SIZE = 10;

    public static boolean fieldValidator(int[][] field) {
        return new BattleField().isValidBattleShipMap(field);
    }

    public boolean isValidBattleShipMap(int[][] field) {
        if (!isValidSize(field)) {
            return false;
        }
        try {
            Map<ShipType, List<Ship>> shipMap = readShips(field);
            validateShipTypes(shipMap);
            return noOverlap(field, shipMap);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidSize(int[][] field) {
        return field.length == BOARD_SIZE && Arrays.stream(field).allMatch(row -> row.length == BOARD_SIZE);
    }

    private Map<ShipType, List<Ship>> readShips(int[][] field) {
        List<Ship> shipList = new ArrayList<>();
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field.length; y++) {
                if (field[x][y] == 1) {
                    Ship ship = createShip(field, new Point(x, y));
                    shipList.add(ship);
                    ship.markAsRead(field);
                }
            }
        }
        return shipList.stream().collect(groupingBy(ShipType::forShip));
    }

    private void validateShipTypes(Map<ShipType, List<Ship>> shipMap) {
        for (ShipType shipType : ShipType.values()) {
            if (!shipMap.containsKey(shipType)) {
                throw new IllegalArgumentException("Missing ships of type " + shipType);
            }
            int actualShipsNumber = shipMap.get(shipType).size();
            if (actualShipsNumber != shipType.getShipsNumber()) {
                throw new IllegalArgumentException(
                        String.format("Wrong amount of ships of type %s. Expected %d but got %d.",
                                shipType, shipType.getShipsNumber(), actualShipsNumber));
            }
        }
    }

    private boolean noOverlap(int[][] field, Map<ShipType, List<Ship>> shipMap) {
        return shipMap.values().stream()
                .flatMap(Collection::stream)
                .flatMap(ship -> ship.borders().stream())
                .allMatch(borderCell -> field[borderCell.x][borderCell.y] == 0);
    }

    private Ship createShip(int[][] field, Point cell) {
        Ship ship = new Ship(cell);
        int dy = cell.y + 1;
        while (dy < field.length && field[cell.x][dy] == 1) {
            ship.addCell(new Point(cell.x, dy));
            dy++;
        }
        int dx = cell.x + 1;
        while (dx < field.length && field[dx][cell.y] == 1) {
            ship.addCell(new Point(dx, cell.y));
            dx++;
        }
        return ship;
    }
}

class Ship {
    private final List<Point> cells;

    Ship(Point cell) {
        cells = new ArrayList<>();
        cells.add(cell);
    }

    void addCell(Point cell) {
        cells.add(cell);
    }

    void markAsRead(int[][] field) {
        cells.forEach(cell -> field[cell.x][cell.y] = -1);
    }

    List<Point> borders() {
        List<Point> borders = new ArrayList<>();
        Point first = cells.get(0);
        Point last = cells.get(cells.size() - 1);
        int iMin;
        int iMax;
        int jMin;
        int jMax;
        boolean horizontal = first.x == last.x;
        if (horizontal) {
            iMin = first.y - 1;
            iMax = last.y + 1;
            jMin = first.x - 1;
            jMax = first.x + 1;
        } else {
            iMin = first.x - 1;
            iMax = last.x + 1;
            jMin = first.y - 1;
            jMax = first.y + 1;
        }
        for (int i = iMin; i <= iMax; i++) {
            for (int j = jMin; j <= jMax; j++) {
                Point cell = horizontal ? new Point(j, i) : new Point(i, j);
                if (!cells.contains(cell)) {
                    borders.add(cell);
                }
            }
        }
        return borders.stream()
                .filter(cell -> cell.x >= 0 && cell.x < BattleField.BOARD_SIZE
                        && cell.y >= 0 && cell.y < BattleField.BOARD_SIZE)
                .collect(Collectors.toList());
    }

    int size() {
        return cells.size();
    }

    @Override
    public String toString() {
        return cells.stream()
                .map(cell -> String.format("(%d, %d)", cell.x, cell.y))
                .collect(joining(", "));
    }
}

enum ShipType {
    BATTLESHIP(4, 1),
    CRUISER(3, 2),
    DESTROYER(2, 3),
    SUBMARINE(1, 4);

    private final int cellsNumber;
    private final int shipsNumber;

    ShipType(int cellsNumber, int shipsNumber) {
        this.cellsNumber = cellsNumber;
        this.shipsNumber = shipsNumber;
    }

    int getShipsNumber() {
        return shipsNumber;
    }

    static ShipType forShip(Ship ship) {
        return forCellsNumber(ship.size());
    }

    private static ShipType forCellsNumber(int shipCellsNumber) {
        for (ShipType type : ShipType.values()) {
            if (type.cellsNumber == shipCellsNumber) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("No ship type of size = %d.", shipCellsNumber));
    }
}