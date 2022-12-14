package ru.croc.task16;

import java.io.IOException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        TaxiSearcher taxiSearcher = new TaxiSearcher();
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Сервис поможет найти наиболее подходящее такси. \nВведите три строки: 1) свое местоположение <широта>, <долгота>;\n" +
                    "2) класс комфорта;\n" +
                    "3) особые пожелания через запятую.");
            try {
                String requestLocation = in.nextLine();
                String requestComfortClass = in.nextLine();
                String requestSpecialRequests = in.nextLine();
                System.out.println("Самый подходящий водитель: " + taxiSearcher.processRequest(requestLocation, requestComfortClass, requestSpecialRequests));
            } catch (IOException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
