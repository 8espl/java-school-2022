package ru.croc.task15;

import java.util.SortedSet;
import java.util.TreeSet;

public class AgeGroup {
    private int startAge;
    private int endAge;
    private SortedSet<Person> groupMembers = new TreeSet<>((p1, p2) -> {
        if (p1.getAge() != p2.getAge()) {
            return (-1 * Integer.compare(p1.getAge(), p2.getAge()));
        } else {
            return p1.getName().compareTo(p2.getName());
        }
    });

    public AgeGroup(int startAge, int endAge) {
        this.startAge = startAge;
        this.endAge = endAge;
    }

    void addMember(Person newMember) {
        groupMembers.add(newMember);
    }

    @Override
    public String toString() {
        StringBuilder groupPrint = new StringBuilder();

        if (SplitterByAge.MAX_AGE != getEndAge()) {
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

    public SortedSet<Person> getGroupMembers() {
        return groupMembers;
    }
}