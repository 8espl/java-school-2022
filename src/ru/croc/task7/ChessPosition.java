package ru.croc.task7;

import static java.lang.Character.getNumericValue;

public class ChessPosition {
    private int x;
    private int y;

    public ChessPosition(int x, int y) throws IllegalPositionException {
        if ((x < 0) || (x > 7)) {
            throw new IllegalPositionException("First coordinate of chess piece position is invalid. " +
                    "It should be in range from 'a' to 'h'");
        }
        if ((y < 0) || (y > 7)) {
            throw new IllegalPositionException("Second coordinate of chess piece position is invalid. " +
                    "It should be in range from '1' to '8'");
        }

        this.x = x;
        this.y = y;
    }

    static ChessPosition parse(String position) throws IllegalPositionException {

        if (position.length() != 2) {
            throw new IllegalPositionException("Chess piece position " + position +
                    " is invalid. Position must be 2 characters long.");
        }

        int x = 0;
        int y = getNumericValue(position.charAt(1)) - 1;
        char currentAlphabetPosition = position.charAt(0);

        switch (currentAlphabetPosition) {
            case 'a' -> {
                x = 0;
            }
            case 'b' -> {
                x = 1;
            }
            case 'c' -> {
                x = 2;
            }
            case 'd' -> {
                x = 3;
            }
            case 'e' -> {
                x = 4;
            }
            case 'f' -> {
                x = 5;
            }
            case 'g' -> {
                x = 6;
            }
            case 'h' -> {
                x = 7;
            }
            default -> {
                throw new IllegalPositionException("First coordinate of chess piece '" + currentAlphabetPosition + "' position is invalid." +
                        "It should be in range from 'a' to 'h'");
            }
        }

        return new ChessPosition(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        String[] alphabetPositions = {"a", "b", "c", "d", "e", "f", "g", "h"};
        return alphabetPositions[x] + Integer.toString(y + 1);
    }
}
