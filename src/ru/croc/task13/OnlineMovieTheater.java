package ru.croc.task13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class OnlineMovieTheater {
    static final private String moviesListPath = "C:/Users/№1/IdeaProjects/java-school-2022/src/ru/croc/task13/movies.txt";
    static final private String usersHistoryPath = "C:/Users/№1/IdeaProjects/java-school-2022/src/ru/croc/task13/watch_history.txt";

    public static void main(String[] args) {
        List<String> serviceHistory = null;
        List<String> serviceMovies = null;

        try {
            serviceMovies = Files.readAllLines(Paths.get(moviesListPath));
            serviceHistory = Files.readAllLines(Paths.get(usersHistoryPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Фильмы на нашем сервисе:" + serviceMovies);
        System.out.println("Введите свою историю просмотра фильмов. Например: 2,8,1,1");
        Scanner in = new Scanner(System.in);
        String userHistory = in.nextLine();
        User user = new User(userHistory);

        MovieRecommender movieRecommender = new MovieRecommender(serviceMovies, serviceHistory);
        System.out.println("Рекомендуем к просмотру фильм \"" + movieRecommender.Recommendation(user) + "\"");
    }
}
