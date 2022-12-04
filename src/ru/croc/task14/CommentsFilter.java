package ru.croc.task14;

import java.util.List;
import java.util.function.Predicate;

public class CommentsFilter<T> implements BlackListFilter<T> {

    Predicate<T> prediction;

    public CommentsFilter(Predicate<T> prediction) {
        this.prediction = prediction;
    }

    public List<T> filterComments(Iterable<T> comments) {
        return filterComments(comments, prediction);
    }
}

