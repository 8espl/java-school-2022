package ru.croc.task5;

public class Annotation {
    private final String name;
    private final Figure figure;

    Annotation(Figure figure, String name) {
        this.name = name;
        this.figure = figure;
    }

    public String getName() {
        return name;
    }

    public Figure getFigure() {
        return figure;
    }

    @Override
    public String toString() {
        return this.figure.getDescription() + ": " + this.name;
    }
}
