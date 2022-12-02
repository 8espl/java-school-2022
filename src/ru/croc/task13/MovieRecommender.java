package ru.croc.task13;

import java.util.*;

public class MovieRecommender {
    private Map<Integer, String> serviceMovies;
    private List<String> serviceHistory;

    public MovieRecommender(Map<Integer, String> serviceMovies, List<String> serviceHistory) {
        this.serviceHistory = serviceHistory;
        this.serviceMovies = serviceMovies;
    }

    public String createRecommendation(User user) {
        Map<Integer, String> movies = new HashMap<>(serviceMovies);
        // находим пользователей, на основе которых cчитается вес рекомендации
        Map<User, Double> similarUsers = new HashMap<>();

        for (String cacheHistory : serviceHistory) {
            User cacheUser = new User(cacheHistory);
            double similarity = compareUsers(cacheUser, user);
            if (similarity >= 0.5) {
                similarUsers.put(cacheUser, similarity);
            }
        }

        int recommendationID = bestRecommendation(similarUsers, user, movies);
        if (recommendationID == -1) {
            // случай, когда вкус пользователя хотя бы на 50% не совпал ни с кем из другмх пользователей
            return "К сожалению, мы не можем составить для вас рекомендацию. Посмотрите любой фильм.";
        } else {
            return movies.get(recommendationID);
        }
    }

    /**
     * @param cacheUser один из пользователей сервиса
     * @param user      пользователь, для которого составляем рекомендацию
     * @return вес рекомендаций пользователя сервиса
     */
    public double compareUsers(User cacheUser, User user) {
        // истории просмотра пользователя, для которого ищем рекомендацию, и пользователя из кэша сервиса
        List<String> cacheUserHistory = Arrays.asList(cacheUser.getHistory().split(","));
        List<String> userHistory = Arrays.asList(user.getHistory().split(","));

        // i-тая ячейка - максимальное количество общих просмотров фильма i
        int[] sameWatchedMovies = new int[serviceMovies.size()];

        for (int i = 0; i < serviceMovies.size(); ++i) {
            // находим частоту просмотра каждого фильма у каждого пользователя и выбираем минимальное
            int userCount = Collections.frequency(userHistory, String.valueOf(i + 1));
            int cacheUserCount = Collections.frequency(cacheUserHistory, String.valueOf(i + 1));
            sameWatchedMovies[i] = Math.min(userCount, cacheUserCount);
        }

        return (double) Arrays.stream(sameWatchedMovies).sum() / userHistory.size();
    }

    /**
     * @param similarUsers <пользователи сервиса со схожим вкусов, вес их рекомендаций>
     * @param user         пользователь, для которого составляем рекомендацию
     * @param movies       все фильмы сервиса
     * @return непросмотренный фильм, который чаще всего смотрели пользователи со схожими вкусами
     */
    public int bestRecommendation(Map<User, Double> similarUsers, User user, Map<Integer, String> movies) {
        int maxRecID = -1;  // максимальный рейтинг(количество просмотров * сумма веса рекомендаций)
        Map<Integer, Double> moviesRating; // непросмотренный фильм и его общий вес рекомендаций
        Map<Integer, Integer> moviesViews; // количество просмотров фильмов, которые пользователь еще не смотрел
        List<String> userHistory = Arrays.asList(user.getHistory().split(","));

        // оставляем в списке фильмов только непросмотренные пользователем
        for (int i = 0; i < serviceMovies.size(); ++i) {
            if (userHistory.contains(String.valueOf(i + 1))) {
                movies.remove(i + 1);
            }
        }

        // если есть фильмы, которые пользователь не смотрел
        if (movies.size() != 0) {
            moviesRating = countMoviesRating(similarUsers, movies);
            moviesViews = countMoviesViews(movies);

            double maxRec = 0.;
            double rec = 0.;
            // поиск наибольшего взвешенного рейтинга
            for (Integer id : moviesRating.keySet()) {
                rec = moviesRating.get(id) * moviesViews.get(id);
                if (rec > maxRec) {
                    maxRec = rec;
                    maxRecID = id;
                }
            }

        }
        return maxRecID;
    }

    /**
     * @param similarUsers <пользователи сервиса со схожим вкусов, вес их рекомендаций>
     * @param movies       непросмотренные фильмы
     * @return <фильм, оценка фильма по историям всех пользователей со схожим вкусом>
     */
    public Map<Integer, Double> countMoviesRating(Map<User, Double> similarUsers, Map<Integer, String> movies) {
        Map<Integer, Double> moviesRating = new HashMap<>(); // ID фильма и его рейтинг

        for (User cacheUser : similarUsers.keySet()) {
            String[] cacheUserHistory = cacheUser.getHistory().split(",");
            for (String movie : cacheUserHistory) {
                int movieID = Integer.parseInt(movie);
                // учитываем рейтинг только непросмотренных фильмов
                if (movies.containsKey(movieID)) {
                    // проверяем, встречался ли уже такой фильм, и если встречался, увеличиваем рейтинг
                    moviesRating.merge(movieID, similarUsers.get(cacheUser), Double::sum);
                }
            }
        }
        return moviesRating;
    }

    /**
     * @param movies непросмотренные фильмы
     * @return <фильм, количество просмотров всех пользователей сервиса>
     */
    public Map<Integer, Integer> countMoviesViews(Map<Integer, String> movies) {
        Map<Integer, Integer> moviesViews = new HashMap<>(); // ID фильма и его рейтинг

        for (String cacheHistory : serviceHistory) {
            List<String> cacheUserHistory = Arrays.asList(cacheHistory.split(","));
            for (Integer movie : movies.keySet()) {
                if (cacheUserHistory.contains(String.valueOf(movie))) {
                    moviesViews.merge(movie, 1, Integer::sum);
                }
            }
        }
        return moviesViews;
    }
}
