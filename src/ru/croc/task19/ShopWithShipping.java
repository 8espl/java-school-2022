package ru.croc.task19;

import ru.croc.task19.DAO.CourierDAO;
import ru.croc.task19.DAO.UserDAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class ShopWithShipping {
    public static void main(String[] args) {
        //args = new String[]{"src/ru/croc/task19/dataWithShipping.CSV"};
        String connectionURL = "jdbc:h2:~/ShopWithShipping";
        String user = "sa";
        String password = "";

        NewDataBaseImporter importer = new NewDataBaseImporter(args[0]);
        importer.loadData(connectionURL, user, password);

        try (Connection connection = DriverManager.getConnection(connectionURL, user, password)) {

            CourierDAO courierDAO = new CourierDAO(connection);
            String courierID = "7900134568";
            System.out.println("Orders to ship for courier '" + courierID + "': ");
            courierDAO.findOrderToShip("78128004567")
                    .forEach((orderID, userLogin) -> System.out.println("Order #" + orderID + " to user '"+ userLogin + "'"));

            UserDAO userDao = new UserDAO(connection);
            String userLogin = "nikita";
            System.out.println("Orders to be delivered to user '" + userLogin + "': ");
            userDao.findShippingInfo(userLogin)
                    .forEach((time, courierFullName) -> System.out.println(time + " by '"+ courierFullName + "'"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}