package ru.croc.task12;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceCensorship {

    public static void main(String[] args) {
        List<String> comments = Arrays.asList("There is peace\n even in the storm",
                                              "Peace not war",
                                              "No war just peace");
        Set<String> blackList = new HashSet<>(Arrays.asList("peace", "war"));

        CommentsFilter censor = new CommentsFilter();
        censor.filterComments(comments, blackList);
        System.out.println(comments);
    }
}
