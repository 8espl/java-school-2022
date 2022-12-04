package ru.croc.task14;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class ServiceCensorship {
    private static Set<String> blackList = new HashSet<>(Arrays.asList("peace", "war"));

    public static void main(String[] args) {
        List<String> comments = Arrays.asList("There is peace\n even in the storm",
                "Peace not war", "No war just peace", "relax, nothing happened", "It's all good now");

        Predicate<String> prediction = (comment) -> {
            for (String word : blackList) {
                if (comment.matches("(?s).*\\b(?i)"+ word + "\\b.*"))
                    return true;
            }
            return false;
        };

        CommentsFilter<String> censor = new CommentsFilter<>(prediction);
        List<String> filteredComments = censor.filterComments(comments);
        System.out.println(filteredComments);
    }
}
