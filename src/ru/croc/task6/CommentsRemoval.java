package ru.croc.task6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentsRemoval {
    public static void main(String[] args) {

        String source = ""; // строка для содержания файла
        String noComments = ""; // строка с отредактированным содержанием файла
        String sourcePath = "C:/Users/№1/IdeaProjects/java-school-2022/src/ru/croc/task6/input.txt";
        String resultPath = "C:/Users/№1/IdeaProjects/java-school-2022/src/ru/croc/task6/output.txt";

        try {
            source = new String(Files.readString(Paths.get(sourcePath)));
            System.out.println(source);
            noComments = removeJavaComments(source);
            System.out.println(noComments);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.writeString(Paths.get(resultPath), noComments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String removeJavaComments(String source) {
        Pattern pattern = Pattern.compile("\\/\\*([^*]|\\*(?!\\/))*\\*\\/|" +
                "\\/\\/[^\\n\'\"]*+(\\n|$)");

        Matcher matcher = pattern.matcher(source);

        return matcher.replaceAll("");
    }
}