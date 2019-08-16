package com.tanks.game.level;

public enum TileType {

    EMPTY(0), BRICK(1), GRASS(2), METAL(3), WHATER(4), VOID(5), ICE(6), SHADOW(7), NUM_ENEMIES(8), HEADQUARTERS_SHADOW(9), THASH(10), HEADQUARTERS(11);

    private int n;

    TileType(int n) {
        this.n = n;
    }

    public int numeric() {
        return n;
    }

    public static TileType fromNumeric(int n) {
        switch (n) {
            case 1:
                return BRICK;
            case 2:
                return GRASS;
            case 3:
                return METAL;
            case 5:
                return VOID;
            case 7:
                return SHADOW;
            case 8:
                return NUM_ENEMIES;
            case 9:
                return HEADQUARTERS_SHADOW;
            case 11:
                return HEADQUARTERS;
            default:
                return EMPTY;
        }
    }
}
