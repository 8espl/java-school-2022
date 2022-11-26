package ru.croc.task12;

import java.util.List;
import java.util.Set;

public class CommentsFilter implements BlackListFilter {
    @Override
    public void filterComments(List<String> comments, Set<String> blackList) {

        for (int i = 0; i < comments.size(); ++i){
            for (String word : blackList){
                comments.set(i, comments.get(i).replaceAll("\\b(?i)"+ word + "\\b", "*".repeat(word.length())));
            }
        }
    }
}
