package ru.croc.task7;

public class Chess {
    public static void main(String[] args) {
        try {
            String[] testPositions = new String[] {"g8", "e7", "c10"};
            //String[] testPositions = args;

            // если позиция одна, то выводим только ее, если больше, то двигаем коня
            if (testPositions.length == 1) {
                System.out.println(ChessPosition.parse(testPositions[0]).toString());
            } else {
                KnightChessPiece.move(testPositions);
                System.out.println("OK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
