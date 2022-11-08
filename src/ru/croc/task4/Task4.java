package ru.croc.task4;

public class Task4 {

    public static void main(String[] args) {
        Annotation a1 = new Annotation(new Circle(
                new Point(100, 100), 50),
                "Tree");
        Annotation a2 = new Annotation(new Rectangle(
                new Point(100,100), new Point(150,200)),
                "Car");

        AnnotatedImage annotatedImage = new AnnotatedImage("randomPath", a1, a2);

        annotatedImage.printAnnotations();
    }
}

