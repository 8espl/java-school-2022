package ru.croc.task14;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface BlackListFilter<T> {
    /**
     * From the given iterable collection of comments removes ones
     * that contain words from the black list.
     *
     * @param comments   iterable collection of comments; every comment
     *                   is a sequence of words, separated
     *                   by spaces, punctuation or line breaks
     * @param prediction predicts whether there are words that should not be in a comment
     */
    default List<T> filterComments(Iterable<T> comments, Predicate<T> prediction) {
        List<T> filteredComments = new ArrayList<>();
        for (T comment : comments) {
            if (!prediction.test(comment)) {
                filteredComments.add(comment);
            }
        }
        return filteredComments;
    }
}
