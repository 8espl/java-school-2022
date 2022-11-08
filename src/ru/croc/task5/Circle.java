package ru.croc.task5;

public class Circle extends Figure {
    private Point center;
    private int radius;

    Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public String getDescription() {
        return "Circle: (" + center.getX() + ", " + center.getY() + "), " + radius;
    }

    @Override
    public boolean pointContainCheck(Point point) {
        return (Math.pow(center.getX() - point.getX(), 2) +
                Math.pow(center.getY() - point.getY(), 2)) <= Math.pow(radius, 2);
    }

    @Override
    public void move(int dx, int dy) {
        center.setX(center.getX() + dx);
        center.setY(center.getY() + dy);
    }
}
