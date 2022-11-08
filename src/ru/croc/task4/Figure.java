package ru.croc.task4;

public abstract class Figure {
    public abstract String getDescription();
}


class Point {
    private final int x;
    private final int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}


class Rectangle extends Figure {
    private Point leftBottomPoint;
    private Point rightUpperPoint;

    Rectangle(Point leftBottomPoint, Point rightUpperPoint) {
        this.leftBottomPoint = leftBottomPoint;
        this.rightUpperPoint = rightUpperPoint;
    }

    @Override
    public String getDescription() {
        return "Rectangle: (" + leftBottomPoint.getX() + ", " + leftBottomPoint.getY() + "), (" + rightUpperPoint.getX() + ", " + rightUpperPoint.getY() + ")";
    }
}


class Circle extends Figure {
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
}
