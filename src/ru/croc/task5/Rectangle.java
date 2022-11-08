package ru.croc.task5;

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

    @Override
    public boolean pointContainCheck(Point point) {
        return ((point.getX() >= leftBottomPoint.getX()) && (point.getX() <= rightUpperPoint.getX())) &&
                ((point.getY() >= leftBottomPoint.getY()) && (point.getY() <= rightUpperPoint.getY()));
    }

    @Override
    public void move(int dx, int dy) {
        leftBottomPoint.setX(leftBottomPoint.getX() + dx);
        leftBottomPoint.setY(leftBottomPoint.getY() + dy);
        rightUpperPoint.setX(rightUpperPoint.getX() + dx);
        rightUpperPoint.setY(rightUpperPoint.getY() + dy);
    }
}
