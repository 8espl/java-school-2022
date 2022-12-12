package ru.croc.task18.DAO;

import ru.croc.task17.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {
    private Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Поиск в базе данных товара с указанным артикулом. Если соответствующего товара в базе данных нет, метод возвращает null.
     */
    public Product findProduct(String productCode) throws SQLException {
        Product product = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM products WHERE code = ?;")) {
            statement.setString(1, productCode);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    product = new Product(result.getString("code"),
                            result.getString("name"),
                            result.getInt("price"));
                }
            }
        }
        return product;
    }

    /**
     * Создание нового товара. Если в базе данных существует товар с переданным артикулом, метод выбрасывает исключение.
     */
    public Product createProduct(Product product) throws Exception {
        if (findProduct(product.getCode()) != null) {
            throw new Exception("Product with code \"" + product.getCode() + "\" already exists in DB.");
        } else {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO products VALUES(?, ?, ?);")) {
                statement.setString(1, product.getCode());
                statement.setString(2, product.getName());
                statement.setInt(3, product.getPrice());
                statement.execute();
            }
        }
        return product;
    }

    /**
     * Изменение информации о товаре. Название и цена товара в базе данных заменяется на значения,
     * указанные в полях параметра product. Артикул товара, данные которого должны быть изменены, также задается полем объекта product.
     */
    public Product updateProduct(Product product) throws Exception {
        if (findProduct(product.getCode()) == null) {
            throw new Exception("Product with code " + product.getCode() + " doesn't exist in DB.");
        } else {
            try (PreparedStatement statement = connection.prepareStatement
                    ("UPDATE products SET name = ?, price = ? WHERE code = ?;")) {
                statement.setString(1, product.getName());
                statement.setInt(2, product.getPrice());
                statement.setString(3, product.getCode());
                statement.execute();
            }
        }
        return product;
    }

    /**
     * Удаление товара и всех упоминаний о нем в заказах.
     */
    public void deleteProduct(String productCode) throws Exception {
        if (findProduct(productCode) == null) {
            throw new Exception("Product with code " + productCode + " doesn't exist in DB.");
        } else {
            try (PreparedStatement statement = connection.prepareStatement
                    ("DELETE FROM order_items oi WHERE oi.product_code = ?;" +
                            "DELETE from orders o WHERE NOT EXISTS (SELECT * FROM order_items oi WHERE oi.order_id = o.id);" +
                            "DELETE FROM products p WHERE p.code = ?;")) {
                statement.setString(1, productCode);
                statement.setString(2, productCode);
                statement.execute();
            }
        }
    }
}
