package ru.croc.task17;

public class Shop {
    public static void main(String[] args) {
        args = new String[] {"src/ru/croc/task17/data.CSV"};
        String connectionURL = "jdbc:h2:~/Shop";
        String user = "sa";
        String password = "";

        DataBaseImporter importer = new DataBaseImporter(args[0]);
        importer.loadData(connectionURL, user, password);
    }
}
