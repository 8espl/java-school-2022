package ru.croc.task15;

import java.util.Objects;

public class Person implements Comparable<Person> {
    private String name;
    private int age;

    public Person(String info) {
        this.name = info.substring(0, info.indexOf(","));
        this.age = Integer.parseInt(info.substring(info.indexOf(",") + 1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Person))
            return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public int compareTo(Person p) {
        if (this.age == p.age) {
            return this.name.compareTo(p.name);
        } else {
            return Integer.compare(this.age, p.age);
        }
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}