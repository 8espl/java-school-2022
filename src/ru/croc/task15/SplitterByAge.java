package ru.croc.task15;

import java.util.*;
import java.util.stream.Stream;

public class SplitterByAge {
    public final static int MIN_AGE = 0;
    public final static int MAX_AGE = 123;

    public void split(String[] ageGroupBounds) throws Exception {
        List<Integer> ages = Arrays.stream(Stream.of(ageGroupBounds).mapToInt(Integer::parseInt).toArray()).boxed().toList();
        List<AgeGroup> ageGroups = createAgeGroups(ages);

        List<String> respondents = new ArrayList<>();
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Введите респондентов: <ФИО>,<возраст>. Для завершения введите \"END\".");
            while (in.hasNextLine()) {
                String nextPerson = in.nextLine();
                if (nextPerson.equals("END")) break;
                respondents.add(nextPerson);
            }
        }

        splitToAgeGroups(respondents, ageGroups);

        for (AgeGroup ageGroup : ageGroups) {
            if (!ageGroup.getGroupMembers().isEmpty()) {
                System.out.println(ageGroup);
            }
        }
    }

    private List<AgeGroup> createAgeGroups(List<Integer> ages) throws Exception {
        List<AgeGroup> groups = new ArrayList<>(ages.size() + 1);

        // проверяем, правильно ли были введены границы возрастных групп
        boolean correct = ages.stream().allMatch(val -> val > -1 && val < 124);
        if (correct) {
            groups.add(new AgeGroup(MIN_AGE, ages.get(0)));
            for (int i = 1; i < ages.size(); ++i) {
                groups.add(new AgeGroup((ages.get(i - 1) + 1), ages.get(i)));
            }
            groups.add(new AgeGroup(ages.get(ages.size() - 1) + 1, MAX_AGE));

            // сортируем возрастные группы в порядке от старшей к младшей
            groups.sort((g1, g2) -> -Integer.compare(g1.getStartAge(), (g2.getStartAge())));

            return groups;
        } else {
            throw new Exception("Неправильно введены границы возрастных групп! Минимальный возраст = " +
                    MIN_AGE + ", максимальный возраст = " + MAX_AGE + ".");
        }
    }

    private void splitToAgeGroups(List<String> respondents, List<AgeGroup> groups) throws Exception {
        for (String respondent : respondents) {
            Person person = new Person(respondent);
            int age = person.getAge();
            if (age > MAX_AGE || age < MIN_AGE) {
                throw new Exception("Неправильно введен возраст у: " + person.getName() + "! Минимальный возраст = " +
                        MIN_AGE + ", максимальный возраст = " + MAX_AGE + ".");
            }
            for (AgeGroup group : groups) {
                if (age >= group.getStartAge() && age <= group.getEndAge()) {
                    group.addMember(person);
                    break;
                }
            }
        }
    }
}