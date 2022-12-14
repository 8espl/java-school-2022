package ru.croc.task15;

public class Task15 {
    public static void main(String[] args) {
        //args = new String[]{"18", "25", "35", "45", "60", "80", "100"};
        SplitterByAge splitterByAge = new SplitterByAge();
        try {
            splitterByAge.split(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
