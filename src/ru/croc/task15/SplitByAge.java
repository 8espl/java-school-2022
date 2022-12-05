package ru.croc.task15;

import java.util.*;
import java.util.stream.Stream;

public class SplitByAge {
    private static int minAge = 0;
    private static int maxAge = 123;

    public static void main(String[] args) {
        //args = new String[]{"18", "25", "35", "45", "60", "80", "100"};
        List<Integer> ages = Arrays.stream(Stream.of(args).mapToInt(Integer::parseInt).toArray()).boxed().toList();
        List<AgeGroup> ageGroups = new ArrayList<>();

        try {
            ageGroups = createAgeGroups(ages);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Scanner in = new Scanner(System.in);
        System.out.println("Введите респондентов: <ФИО>,<возраст>. Для завершения введите \"END\".");

        // составляем список респондентов из введенных данных
        List<String> respondents = new ArrayList<>();
        while (in.hasNextLine()) {
            String nextPerson = in.nextLine();
            if (nextPerson.equals("END")) break;
            respondents.add(nextPerson);
        }

        try {
            splitToAgeGroups(respondents, ageGroups);
        } catch (Exception wrongAge) {
            System.out.println(wrongAge.getMessage());
        }

        for (AgeGroup ageGroup : ageGroups) {
            if (!ageGroup.getGroupMembers().isEmpty()) {
                System.out.println(ageGroup);
            }
        }
    }

    static List<AgeGroup> createAgeGroups(List<Integer> ages) throws Exception {
        List<AgeGroup> groups = new ArrayList<>(ages.size() + 1);

        // проверяем, правильно ли были введены границы возрастных групп
        boolean correct = ages.stream().allMatch(val -> val > -1 && val < 124);
        if (correct) {
            groups.add(new AgeGroup(minAge, ages.get(0)));
            for (int i = 1; i < ages.size(); ++i) {
                groups.add(new AgeGroup((ages.get(i - 1)), ages.get(i)));
            }
            groups.add(new AgeGroup(ages.get(ages.size() - 1), maxAge));

            // сортируем возрастные группы в порядке от старшей к младшей
            groups.sort((g1, g2) -> -Integer.compare(g1.getStartAge(), (g2.getStartAge())));

            return groups;
        } else {
            throw new Exception("Неправильно введены границы возрастных групп! Минимальный возраст = " +
                    minAge + ", максимальный возраст = " + maxAge + ".");
        }
    }

    static void splitToAgeGroups(List<String> respondents, List<AgeGroup> groups) throws Exception {
        for (String respondent : respondents) {
            Person person = new Person(respondent);
            int age = person.getAge();
            if (age > maxAge || age < minAge) {
                throw new Exception("Неправильно введен возраст у: " + person.getName() + "! Минимальный возраст = " +
                        minAge + ", максимальный возраст = " + maxAge + ".");
            }
            for (AgeGroup group : groups) {
                if (age >= group.getStartAge() && age <= group.getEndAge()) {
                    group.addMember(person);
                    break;
                }
            }
        }
    }

    public static int getMaxAge() {
        return maxAge;
    }
}
