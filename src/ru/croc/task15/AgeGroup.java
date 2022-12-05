package ru.croc.task15;

import java.util.ArrayList;

public class AgeGroup {
    private int startAge;
    private int endAge;
    private ArrayList<Person> groupMembers = new ArrayList<>();

    public AgeGroup(int startAge, int endAge) {
        this.startAge = startAge;
        this.endAge = endAge;
    }

    void addMember(Person newMember) {
        groupMembers.add(newMember);
        // сортируем респондентов по возрасту в порядке убывания, если возраст совпадает - по ФИО в порядке возрастания
        if (groupMembers.size() > 1) {
            groupMembers.sort((p1, p2) -> {
                if (p1.getAge() != p2.getAge()) {
                    return (-1 * Integer.compare(p1.getAge(), p2.getAge()));
                } else {
                    return p1.getName().compareTo(p2.getName());
                }
            });
        }
    }

    @Override
    public String toString() {
        StringBuilder groupPrint = new StringBuilder();

        if (SplitByAge.getMaxAge() != getEndAge()) {
            groupPrint.append(getStartAge()).append("-").append(getEndAge()).append(": ");
        } else {
            groupPrint.append(getStartAge()).append("+: ");
        }

        for (Person member : groupMembers) {
            groupPrint.append(member.toString()).append(", ");
        }
        groupPrint.deleteCharAt(groupPrint.lastIndexOf(","));

        return groupPrint.toString();
    }

    public int getStartAge() {
        return startAge;
    }

    public int getEndAge() {
        return endAge;
    }

    public ArrayList<Person> getGroupMembers() {
        return groupMembers;
    }
}
