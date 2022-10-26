package ru.croc.task3;

import java.util.Scanner;

public class Triangle {

    static class Point {
        double x;
        double y;
    }

    /*
     * Вычисляет площадь треугольника по трем точкам
     * с помощью свойств векторного произведения
     */
    public static double square(Point a, Point b, Point c) {

        double s;

        s = Math.abs(((b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x))) / 2.;

        return s;
    }

    public static void main(String[] args) {

        Point a = new Point();
        a.x = 0.0;
        a.y = 0.0;

        Point b = new Point();
        b.x = 2.0;
        b.y = 0.0;

        Point c = new Point();
        c.x = 0.0;
        c.y = 2.0;

        Scanner sc = new Scanner(System.in);

        System.out.println("Введите координаты вершин треугольника");
        a.x = sc.nextDouble();
        a.y = sc.nextDouble();
        b.x = sc.nextDouble();
        b.y = sc.nextDouble();
        c.x = sc.nextDouble();
        c.y = sc.nextDouble();

        System.out.println("Площадь треугольника: " + square(a, b, c));
    }
}

