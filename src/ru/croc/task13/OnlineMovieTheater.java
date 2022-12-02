package ru.croc.task13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class OnlineMovieTheater {
    static final private String moviesListPath = "src/ru/croc/task13/movies.txt";
    static final private String usersHistoryPath = "src/ru/croc/task13/history.txt";

    public static void main(String[] args) {
        Map<Integer, String> serviceMovies = null;
        List<String> serviceHistory = null;

        try {
            serviceHistory = getServiceHistory();
            serviceMovies = getServiceMovies();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Фильмы на нашем сервисе:" + serviceMovies.values());

        System.out.println("Введите свою историю просмотра фильмов. Например: 2,8,1,1");
        Scanner in = new Scanner(System.in);
        String userHistory = in.nextLine();
        User user = new User(userHistory);

        MovieRecommender movieRecommender = new MovieRecommender(serviceMovies, serviceHistory);

        System.out.println("Рекомендуем к просмотру: \n" + movieRecommender.createRecommendation(user));
    }

    public static List<String> getServiceHistory() throws IOException {
        return Files.readAllLines(Paths.get(usersHistoryPath));
    }

    public static Map<Integer, String> getServiceMovies() throws IOException {
        List<String> movies = Files.readAllLines(Paths.get(moviesListPath));
        Map<Integer, String> serviceMovies = new HashMap<>();

        for (String movie : movies) {
            serviceMovies.put(Integer.valueOf(movie.substring(0, movie.indexOf(","))),
                    movie.substring(movie.indexOf(",") + 1));
        }
        return serviceMovies;
    }
}
