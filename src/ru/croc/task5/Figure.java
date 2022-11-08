package ru.croc.task5;

public abstract class Figure implements Movable{
    public abstract String getDescription();
    abstract boolean pointContainCheck(Point point);
}


class Point {
    private int x;
    private int y;

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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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

    @Override
    public boolean pointContainCheck(Point point){
        return ((point.getX() >= leftBottomPoint.getX()) && (point.getX() <= rightUpperPoint.getX())) &&
                ((point.getY() >= leftBottomPoint.getY()) && (point.getY() <= rightUpperPoint.getY()));
    }

    @Override
    public void move(int dx, int dy){
        leftBottomPoint.setX(leftBottomPoint.getX() + dx);
        leftBottomPoint.setY(leftBottomPoint.getY() + dy);
        rightUpperPoint.setX(rightUpperPoint.getX() + dx);
        rightUpperPoint.setY(rightUpperPoint.getY() + dy);
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

    @Override
    public boolean pointContainCheck(Point point) {
        return (Math.pow(center.getX()-point.getX(),2) +
                Math.pow(center.getY()-point.getY(),2)) <= Math.pow(radius, 2);
    }

    @Override
    public void move(int dx, int dy){
        center.setX(center.getX() + dx);
        center.setY(center.getY() + dy);
    }
}
