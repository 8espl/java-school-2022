package ru.croc.task7.exceptions;

public class IllegalPositionException extends Exception {

    String message;

    public IllegalPositionException(char x) {
        this.message = "First coordinate of a chess piece position '" + x + "' is invalid. " +
                "It should be in range from 'a' to 'h'";
    }

    public IllegalPositionException(int y) {
        this.message = "Second coordinate of a chess piece position '" + (y + 1) + "' is invalid. " +
                "It should be in range from '1' to '8'";
    }

    public IllegalPositionException(String position) {
        this.message = "Chess piece position " + position +
                " is invalid. Position must be 2 characters long.";
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}


