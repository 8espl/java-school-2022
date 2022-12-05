package ru.croc.task15;

import java.util.Objects;

public class Person implements Comparable {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

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
    public int compareTo(Object o) {
        Person person = (Person) o;
        if (this.age == person.age) {
            return this.name.compareTo(person.name);
        } else {
            return Integer.compare(this.age, person.age);
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
