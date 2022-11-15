package ru.croc.task7;

public class IllegalMoveException extends Exception {
    String message;

    public IllegalMoveException(ChessPosition pos1, ChessPosition pos2) {
        this.message = "Knight doesn't move like that: " + pos1.toString() + " -> " + pos2.toString();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
