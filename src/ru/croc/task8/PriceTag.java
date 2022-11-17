package ru.croc.task8;

import java.text.NumberFormat;
import java.util.IllformedLocaleException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class PriceTag {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("To see possible values of language and region enter \"yes\".");
        if (in.nextLine().equals("yes")) {
            printPossibleLocaleValues();
        }

        System.out.println("Enter locale language: ");
        // обрабатываем ввод, и если случайно введены пробелы, то удаляем их
        String language = in.nextLine().replaceAll("\\s+","");
        System.out.println("Enter locale region: ");
        String region = in.nextLine().replaceAll("\\s+","");

        Locale locale = setNewLocale(language, region);
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        System.out.println("Enter number using example: 11,11");

        while (in.hasNextLine()) {
            // если формат введенного числа неверный, то прерываем программу
            try {
                System.out.println("Result: " + format.format(in.nextBigDecimal()));
            } catch (InputMismatchException e) {
                System.out.println("Wrong number format. Next time enter number using example: 11,11");
                break;
            }
            System.out.println("Enter next number");
        }
    }

    /**
     * Устанавливает региональные настройки и обрабатывает неверно введенные поля locale.
     */
    public static Locale setNewLocale(String language, String region) {
        Scanner in = new Scanner(System.in);

        // если язык и регион не указан, то устанавливаем дефолтные значения
        if (!(language.isEmpty() || region.isEmpty())) {
            // если язык или регион введены с ошибкой, то устанавливаем дефолтные значения
            try {
                return (new Locale.Builder().setRegion(region).setLanguage(language).build());
            } catch (IllformedLocaleException e) {
                System.out.println("Wrong language or region format. Locale values were reset to default values. \n"
                        + "To see possible values of language and region values enter \"yes\".");

                // вывод доступных значений языка и региона(по желанию)
                if (in.nextLine().equals("yes")) {
                    printPossibleLocaleValues();
                }

                System.out.println("Locale values were set to default values");
                return (Locale.getDefault());
            }
        } else {
            System.out.println("Locale values were set to default values");
            return (Locale.getDefault());
        }
    }

    /**
     * Выводит доступные значения полей locale.
     */
    public static void printPossibleLocaleValues() {
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale loc : locales) {
            if (!loc.getCountry().isEmpty())
                System.out.println("Language: " + loc.getLanguage() + "   Region: " + loc.getCountry());
        }
    }

}