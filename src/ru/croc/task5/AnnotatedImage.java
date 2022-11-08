package ru.croc.task5;

public class AnnotatedImage {

    private final String imagePath;
    private final Annotation[] annotations;

    public AnnotatedImage(String imagePath, Annotation... annotations) {
        this.imagePath = imagePath;
        this.annotations = annotations;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public Annotation[] getAnnotations() {
        return this.annotations;
    }

    /**
     * Выбор аннотации по координатам точки (x, y).
     * @return первая аннотация, содержащая данную точку
     *         null, если такое аннотации нет
     */
    public Annotation findByPoint(int x, int y) {
        for (Annotation annotation : annotations) {
            if (annotation.getFigure().pointContainCheck(new Point(x, y))) {
                return annotation;
            }
        }
        System.out.printf("Annotation Was NotFound by point (x=%d, y=%d)", x, y);
        return null;
    }

    /**
     * Выбор аннотации по шаблону подписи.
     * @return первая аннотация, подпись которой содержит заданную подстроку
     *         null, если такое аннотации нет
     */
    public Annotation findByLabel(String label) {
        for (Annotation annotation : annotations) {
            if (annotation.toString().contains(label)) {
                return annotation;
            }
        }
        System.out.println("Annotation Was NotFound by label " + label);
        return null;
    }


    /**
     * Выводит список аннотаций
     */
    public void printAnnotations() {
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }
}
