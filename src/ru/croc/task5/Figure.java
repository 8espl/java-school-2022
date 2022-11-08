package ru.croc.task5;

public abstract class Figure implements Movable {
    public abstract String getDescription();

    abstract boolean pointContainCheck(Point point);
}


