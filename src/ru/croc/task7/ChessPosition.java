package ru.croc.task7;

import ru.croc.task7.exceptions.IllegalPositionException;
import static java.lang.Character.getNumericValue;

public class ChessPosition {
    private int x;
    private int y;

    public ChessPosition(int x, int y) throws IllegalPositionException {
        if ((x < 0) || (x > 7)) {
            throw new IllegalPositionException((char) (x + 'a'));
        }
        if ((y < 0) || (y > 7)) {
            throw new IllegalPositionException(y);
        }

        this.x = x;
        this.y = y;
    }

    static ChessPosition parse(String position) throws IllegalPositionException {

        if (position.length() != 2) {
            throw new IllegalPositionException(position);
        }

        int x = 0;
        int y = getNumericValue(position.charAt(1)) - 1;

        char currentAlphabetPosition = position.charAt(0);

        x = currentAlphabetPosition - 'a';
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
        return alphabetPositions[x] + (y + 1);
    }
}
