package ru.croc.task7;

import ru.croc.task7.exceptions.*;

public class KnightChessPiece {
    public static boolean canMoveCorrectly(ChessPosition pos1, ChessPosition pos2) {
        return Math.abs(pos1.getX() - pos2.getX()) *
                Math.abs(pos1.getY() - pos2.getY()) == 2;
    }

    public static void move(String[] positions) throws IllegalPositionException, IllegalMoveException {

        // парсит текстовую строку с позициями в отдельные позиции
        ChessPosition[] chessPositions = new ChessPosition[positions.length];
        for (int k = 0; k < positions.length; ++k) {
            chessPositions[k] = ChessPosition.parse(positions[k]);
        }

        for (int i = 0; i < positions.length - 1; ++i) {
            if (!canMoveCorrectly(chessPositions[i], chessPositions[i + 1])) {
                throw new IllegalMoveException(chessPositions[i], chessPositions[i + 1]);
            }
        }
    }
}
