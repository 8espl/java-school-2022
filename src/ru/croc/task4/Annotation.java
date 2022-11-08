package ru.croc.task4;
import ru.croc.task4.Figure;

public class Annotation {
    private String name;
    private Figure figure;

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
