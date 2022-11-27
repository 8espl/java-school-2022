package ru.croc.task13;

import java.util.*;

public class MovieRecommender {
    private List<String> serviceMovies;
    private List<String> serviceHistory;

    public MovieRecommender(List<String> serviceMovies, List<String> serviceHistory) {
        this.serviceHistory = serviceHistory;
        this.serviceMovies = serviceMovies;
    }

    public String Recommendation(User user) {

        Map<Integer, String> movies = new HashMap<>();
        for (String movie : serviceMovies) {
            movies.put(Integer.valueOf(movie.substring(0, movie.indexOf(","))),
                    movie.substring(movie.indexOf(",") + 1));
        }

        // пользователи, на основе которых создается рекомендация, с коэффициентом важности рекомендации
        Map<User, Double> usersForRecommendation = new HashMap<>();

        for (String cacheHistory : serviceHistory) {
            User cacheUser = new User(cacheHistory);
            double similarity = CompareUsers(cacheUser, user);
            if (similarity >= 0.5) {
                usersForRecommendation.put(cacheUser, similarity);
            }
        }

        int recommendationID = MostWatchedMovie(usersForRecommendation, user);
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
    public double CompareUsers(User cacheUser, User user) {
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
     * @param usersForRecommendation <пользователи сервиса со схожим вкусов, вес их рекомендаций>
     * @param user                   пользователь, для которого составляем рекомендацию
     * @return непросмотренный фильм, который чаще всего смотрели пользователи со схожими вкусами
     */
    public int MostWatchedMovie(Map<User, Double> usersForRecommendation, User user) {
        int maxRatingMovieID = -1;  // максимальный рейтинг(количество просмотров*вес рекомеендации)
        Map<Integer, Double> moviesRating; // непросмотренный фильм и его общий рейтинг
        List<Integer> moviesIDForRecommendation = new ArrayList<>(); // непросмотренные фильмы, доступные к рекомендации
        List<String> userHistory = Arrays.asList(user.getHistory().split(","));

        // создаем список только непросмотренных пользователем фильмов
        for (int i = 0; i < serviceMovies.size(); ++i) {
            if (!(userHistory.contains(String.valueOf(i + 1)))) {
                moviesIDForRecommendation.add(i + 1);
            }
        }

        // если есть фильмы, которые пользователь не смотрел
        if (moviesIDForRecommendation.size() != 0) {
            moviesRating = CountMoviesRating(usersForRecommendation, moviesIDForRecommendation);
            Double maxRating = Collections.max(moviesRating.values());

            // среди всех фильмов, которые можно порекомендовать, ищем фильм с максимальным найденным рейтингом
            for (Map.Entry<Integer, Double> entry :
                    moviesRating.entrySet()) {
                if (entry.getValue().equals(maxRating)) {
                    maxRatingMovieID = entry.getKey();
                    break;
                }
            }
        }
        return maxRatingMovieID;
    }

    /**
     * считает рейтинг фильмов (которые пользователь еще не смотрел)
     * по историям всех пользователей со схожим вкусом,
     * прибавляя к рейтингу вес рекомендаций каждый раз, когда в истории встречается этот фильм
     */
    public Map<Integer, Double> CountMoviesRating(Map<User, Double> usersForRecommendation, List<Integer> moviesIDForRecommendation) {
        Map<Integer, Double> moviesRating = new HashMap<>();

        for (User cacheUser : usersForRecommendation.keySet()) {
            String[] cacheUserHistory = cacheUser.getHistory().split(",");
            for (String movie : cacheUserHistory) {
                if (moviesIDForRecommendation.contains(Integer.parseInt(movie))) {
                    if (moviesRating.get(Integer.parseInt(movie)) != null) {
                        moviesRating.put(Integer.parseInt(movie), moviesRating.get(Integer.parseInt(movie)) + usersForRecommendation.get(cacheUser));
                    } else {
                        moviesRating.put(Integer.parseInt(movie), usersForRecommendation.get(cacheUser));
                    }
                }
            }
        }
        return moviesRating;
    }
}
