package ru.croc.task5;

public class Task5 {

    public static void main(String[] args) {

        Annotation a1 = new Annotation(new Circle(
                new Point(-5, -5), 5),
                "Tree");
        Annotation a2 = new Annotation(new Rectangle(
                new Point(100, 100), new Point(150, 200)),
                "Car");

        AnnotatedImage annotatedImage = new AnnotatedImage("randomPath", a1, a2);

        annotatedImage.printAnnotations();

        annotatedImage.findByLabel("Rectangle").getFigure().move(1, 1);
        annotatedImage.findByPoint(-6, -6).getFigure().move(2, 2);

        annotatedImage.printAnnotations();

        annotatedImage.findByLabel("Circle").getFigure().move(-5, -5);
        annotatedImage.findByPoint(130, 120).getFigure().move(2, 2);

        annotatedImage.printAnnotations();
    }
}

