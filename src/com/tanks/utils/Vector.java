package com.tanks.utils;

public enum Vector {
    TOP(0), RIGHT(1), DOWN(2), LEFT(3);
    private int n;

    Vector(int n) {
        this.n = n;
    }

    public static Vector fromNumeric(int n) {
        switch (n) {
            case 0:
                return TOP;
            case 1:
                return RIGHT;
            case 2:
                return DOWN;
            case 3:
                return LEFT;
            default:
                System.out.println("Error");
                return null;
        }
    }

    public int numeric() {
        return n;
    }
}
