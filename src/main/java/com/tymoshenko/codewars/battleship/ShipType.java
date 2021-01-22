package com.tymoshenko.codewars.battleship;

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
