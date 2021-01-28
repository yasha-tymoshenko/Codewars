package com.tymoshenko.codewars.battleship;


import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * 3 kyu
 * <p>
 * Battleship field validator
 * <p>
 *
 * @see <a href="Task Description">https://www.codewars.com/kata/52bb6539a4cf1b12d90005b7/train/java</a>
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
                || Arrays.stream(gameMap).anyMatch(row -> row.length != GAME_MAP_SIZE)) {
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

    private void validateNoShipsOverlap(int[][] gameMap, Map<ShipType, List<Ship>> ships) {
        if (ships.values().stream()
                .flatMap(Collection::stream)
                .flatMap(ship -> ship.borders().stream())
                .anyMatch(borderCell -> gameMap[borderCell.x][borderCell.y] != 0)) {
            throw new IllegalArgumentException("Some ships overlap.");
        }
    }
}
