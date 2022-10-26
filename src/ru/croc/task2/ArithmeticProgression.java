package ru.croc.task2;

import java.util.Scanner;

public class ArithmeticProgression {

    /*
     * Вычисляет сумму арифметической прогрессии
     */
    public static double sum(double a, double d, int n) {

        double s = 0.;

        for (int i = 0; i < n; ++i) {
            s += a;
            a += d;
        }

        return s;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Введите начальный элемент," +
                " разность арифметической прогрессии" +
                " и количество членов прогрессии");

        double a = sc.nextDouble();
        double d = sc.nextDouble();
        int n = sc.nextInt();

        System.out.println("Sum: " + sum(a, d, n));
    }
}