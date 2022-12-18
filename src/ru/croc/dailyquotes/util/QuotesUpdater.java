package ru.croc.dailyquotes.util;

import ru.croc.dailyquotes.database.DBConnectionGetter;
import ru.croc.dailyquotes.dao.UserDAO;

import java.sql.SQLException;
import java.util.Set;

public class QuotesUpdater implements Runnable {
    @Override
    public void run() {
        try {
            UserDAO userDAO = new UserDAO(DBConnectionGetter.getConnection());
            Set<Integer> usersID = userDAO.getUsersId();
            RecommendationSystem recommendationSystem = new RecommendationSystem();
            for (int id : usersID) {
                userDAO.updateDailyQuote(id, recommendationSystem.chooseCategory(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
