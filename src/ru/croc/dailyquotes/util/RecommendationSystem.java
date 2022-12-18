package ru.croc.dailyquotes.util;

import ru.croc.dailyquotes.dao.UserDAO;
import ru.croc.dailyquotes.database.DBConnectionGetter;
import ru.croc.dailyquotes.entity.Category;
import ru.croc.dailyquotes.entity.Quote;

import java.sql.SQLException;
import java.util.*;

public class RecommendationSystem {

    /**
     * Выбирает категорию в зависимости от понравившихся прежде цитат.
     * Если цитаты из некоторых категорий никогда не нравились,
     * то вероятность их появление не будет равна 0.
     *
     * @param userId
     * @return категория для ежедневной цитаты
     * @throws SQLException
     */
    public Category chooseCategory(int userId) throws SQLException {
        UserDAO userDAO = new UserDAO(DBConnectionGetter.getConnection());
        List<Quote> likedQuotes = userDAO.getLikedQuotes(userId);
        Map<Category, Integer> likesCategory = new HashMap<>();
        for (Category category : Category.values()) {
            int count = Collections.frequency(likedQuotes, category.toString().toLowerCase());
            if (count > 0) {
                likesCategory.put(category, count);
            } else {
                likesCategory.put(category, 1);
            }
        }
        return randomCategory(likesCategory);
    }

    /**
     * @param likesCategory количество понравившихся цитат из каждй категории для конкретного пользователя
     * @return случайно выбранная категория с вероятностью,
     * пропорциональной количеству понравившихся из этой категории цитат
     */
    private Category randomCategory(Map<Category, Integer> likesCategory) {
        List<Category> temp = new ArrayList<>();
        for (Category category : likesCategory.keySet()) {
            for (int i = 0; i < likesCategory.get(category); ++i) {
                temp.add(category);
            }
        }
        return temp.get(new Random().nextInt(temp.size()));
    }
}
