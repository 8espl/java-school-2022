package ru.croc.task7;

public class KnightChessPiece {
    public static boolean canMoveCorrectly(ChessPosition pos1, ChessPosition pos2) {
        return Math.abs(pos1.getX() - pos2.getX()) *
                Math.abs(pos1.getY() - pos2.getY()) == 2;
    }

    public static void move(String[] positions) throws IllegalPositionException, IllegalMoveException {
        for (int i = 0; i < positions.length - 1; ++i) {
            ChessPosition pos1 = ChessPosition.parse(positions[i]);
            ChessPosition pos2 = ChessPosition.parse(positions[i + 1]);
            if (!canMoveCorrectly(pos1, pos2)) {
                throw new IllegalMoveException(pos1, pos2);
            }
        }
    }
}
