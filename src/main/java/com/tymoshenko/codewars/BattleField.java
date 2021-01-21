package com.tymoshenko.codewars;


import java.awt.*;
import java.util.List;
import java.util.*;

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

    static final int GAME_MAP_SIZE = 10;

    public static boolean fieldValidator(int[][] field) {
        return new BattleField().isValidBattleShipMap(field);
    }

    public boolean isValidBattleShipMap(int[][] gameMap) {
        boolean valid = false;
        try {
            validateSize(gameMap);
            Map<ShipType, List<Ship>> ships = readShips(gameMap);
            validateShipTypes(ships);
            validateNoShipsOverlap(gameMap, ships);
            valid = true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return valid;
    }

    private void validateSize(int[][] gameMap) {
        if (gameMap.length != GAME_MAP_SIZE
                || !Arrays.stream(gameMap).allMatch(row -> row.length == GAME_MAP_SIZE)) {
            throw new IllegalArgumentException(
                    String.format("Wrong battle field size. Expected %dx%d.", GAME_MAP_SIZE, GAME_MAP_SIZE));
        }
    }

    private Map<ShipType, List<Ship>> readShips(int[][] gameMap) {
        List<Ship> ships = new LinkedList<>();
        for (int x = 0; x < gameMap.length; x++) {
            for (int y = 0; y < gameMap.length; y++) {
                if (gameMap[x][y] == 1) {
                    Ship ship = Ship.create(gameMap, x, y);
                    ships.add(ship);
                    ship.markAsRead(gameMap);
                }
            }
        }
        return ships.stream().collect(groupingBy(ShipType::forShip));
    }

    private void validateShipTypes(Map<ShipType, List<Ship>> ships) {
        for (ShipType shipType : ShipType.values()) {
            if (!ships.containsKey(shipType)) {
                throw new IllegalArgumentException("Missing ships of type " + shipType);
            }
            int actualShipsNumber = ships.get(shipType).size();
            if (actualShipsNumber != shipType.getShipsNumber()) {
                throw new IllegalArgumentException(
                        String.format("Wrong amount of ships of type %s. Expected %d but got %d.",
                                shipType, shipType.getShipsNumber(), actualShipsNumber));
            }
        }
    }

    private void validateNoShipsOverlap(int[][] field, Map<ShipType, List<Ship>> shipMap) {
        if (shipMap.values().stream()
                .flatMap(Collection::stream)
                .flatMap(ship -> ship.borders().stream())
                .anyMatch(borderCell -> field[borderCell.x][borderCell.y] != 0)) {
            throw new IllegalArgumentException("Some ships overlap.");
        }
    }
}

class Ship {
    private final List<Point> cells;

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

    List<Point> borders() {
        List<Point> borders = new ArrayList<>();
        Point firstCell = cells.get(0);
        Point lastCell = cells.get(cells.size() - 1);
        int iMin;
        int iMax;
        int jMin;
        int jMax;
        boolean horizontal = firstCell.x == lastCell.x;
        iMin = horizontal ? firstCell.y - 1 : firstCell.x - 1;
        iMax = horizontal ? lastCell.y + 1 : lastCell.x + 1;
        jMin = horizontal ? firstCell.x - 1 : firstCell.y - 1;
        jMax = horizontal ? firstCell.x + 1 : firstCell.y + 1;
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