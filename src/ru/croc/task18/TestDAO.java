package ru.croc.task18;

import ru.croc.task17.entity.*;
import ru.croc.task18.DAO.OrderDAO;
import ru.croc.task18.DAO.ProductDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;

public class TestDAO {
    public static void main(String[] args) {
        String connectionURL = "jdbc:h2:~/Shop";
        String user = "sa";
        String password = "";

        try (Connection connection = DriverManager.getConnection(connectionURL, user, password)) {
            OrderDAO orderDAO = new OrderDAO(connection);
            ProductDAO productDAO = new ProductDAO(connection);

            System.out.println(productDAO.createProduct(new Product("Т8", "Графический планшет", 5000)));
            productDAO.deleteProduct("Т5");
            productDAO.deleteProduct("Т4");
            System.out.println(productDAO.findProduct("Т4"));
            System.out.println(productDAO.findProduct("Т8"));
            System.out.println(productDAO.updateProduct(new Product("Т2", "Беспроводная мышь", 100)));
            System.out.println(orderDAO.createOrder("elvina", Arrays.asList(new Product("Т8", "Графический планшет", 5000),
                    new Product("Т8", "Графический планшет", 5000), new Product("Т2", "Беспроводная мышь", 100))));

            // запросы, которые выдают ошибки
            System.out.println(productDAO.createProduct(new Product("Т2", "Беспроводная мышь", 100)));
            System.out.println(orderDAO.createOrder("elvina", Arrays.asList(new Product("Т10", "Фотоаппарат", 50000),
                    new Product("Т8", "Графический планшет", 5000), new Product("Т2", "Беспроводная мышь", 100))));

            System.out.println("Database has been accessed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
