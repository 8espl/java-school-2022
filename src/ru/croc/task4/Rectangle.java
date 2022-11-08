package ru.croc.task4;

public class Rectangle extends Figure {
    private final Point leftBottomPoint;
    private final Point rightUpperPoint;

    Rectangle(Point leftBottomPoint, Point rightUpperPoint) {
        this.leftBottomPoint = leftBottomPoint;
        this.rightUpperPoint = rightUpperPoint;
    }

    @Override
    public String getDescription() {
        return "Rectangle: (" + leftBottomPoint.getX() + ", " + leftBottomPoint.getY() + "), (" + rightUpperPoint.getX() + ", " + rightUpperPoint.getY() + ")";
    }
}
