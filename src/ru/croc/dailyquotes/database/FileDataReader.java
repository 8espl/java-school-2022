package ru.croc.dailyquotes.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.croc.dailyquotes.entity.Quote;
import ru.croc.dailyquotes.entity.User;

import java.io.*;
import java.util.List;
import java.util.Set;

public class FileDataReader {
    private final String QUOTE_SOURCE;
    private final String CLIENT_INFO_SOURCE;

    public FileDataReader(String quotesSource, String userInfoSource) {
        this.QUOTE_SOURCE = quotesSource;
        this.CLIENT_INFO_SOURCE = userInfoSource;
    }

    public void readFiles(List<Quote> quotes, Set<User> users) throws IOException {
        readQuotes(quotes);
        readUsers(users);
    }

    private void readQuotes(List<Quote> quotes) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        quotes.addAll(mapper.reader().forType(new TypeReference<List<Quote>>() {
        }).readValue(new File(QUOTE_SOURCE)));

        /* удаляем неправильно созданные данные таблицы (случай, когда цитаты попали в поле автора)
         * или слишком длинный цитаты, которые не поместятся на экран
         */
        quotes.removeIf(quote -> quote.getAuthor().length() > 255 || quote.getText().length() > 500);
    }

    private void readUsers(Set<User> users) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(CLIENT_INFO_SOURCE))) {
            String line;
            String[] info;
            while ((line = reader.readLine()) != null) {
                info = line.split(",");
                int userId = Integer.parseInt(info[0]);
                int dailyQuoteId = Integer.parseInt(info[1]);
                User user = new User(userId, dailyQuoteId);
                users.add(user);
            }
        }
    }
}
