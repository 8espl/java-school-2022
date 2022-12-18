package ru.croc.dailyquotes.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBExistenceChecker {
    /**
     * Проверяет в базе данных, существуют ли нужный для модуля таблицы.
     *
     * @return true, если таблицы уже созданы
     * false, если таблиц нет
     * @throws SQLException
     */
    public static boolean checkExistence() throws SQLException {
        List<String> tables = new ArrayList<>();
        try (ResultSet resultSet = DBConnectionGetter.getConnection().getMetaData()
                .getTables("LANGUAGELEARNINGAPP", null, null, null)) {
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                tables.add(tableName);
            }
        }
        boolean exists = (tables.contains("USER_INFO") && tables.contains("QUOTE_INFO")
                && tables.contains("LIKE_INFO"));
        return exists;
    }
}
