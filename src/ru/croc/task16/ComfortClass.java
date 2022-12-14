package ru.croc.task16;

public enum ComfortClass {

    ECONOMY("эконом", 1),
    STANDARD("стандарт", 1.5F),
    COMFORT("комфорт", 3);

    private final String ruComfortClass;
    private float index;

    ComfortClass(String ruComfortClass, float index) {
        this.ruComfortClass = ruComfortClass;
        this.index = index;
    }

    public static ComfortClass getComfortClass(String requestComfortClass) {
        for (ComfortClass comfortClass : values()) {
            if (comfortClass.getRuComfortClass().equalsIgnoreCase(requestComfortClass.trim())) {
                return comfortClass;
            }
        }
        throw new IllegalArgumentException("Неправильно введен класс комфорта \"" + requestComfortClass + "\"!");
    }

    public String getRuComfortClass() {
        return ruComfortClass;
    }

    public float getIndex() {
        return index;
    }
}
