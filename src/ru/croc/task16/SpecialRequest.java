package ru.croc.task16;

public enum SpecialRequest {
    CHILD_SEAT("детское кресло"),
    TOLL_ROAD("по платной дороге"),
    WITH_A_PET("с питомцем"),
    SUITABLE_FOR_PEOPLE_WITH_DISABILITIES("подходит для людей с ограниченными возможностями"),
    NO_REQUEST("");

    private final String ruSpecialRequest;

    SpecialRequest(String ruSpecialRequest) {
        this.ruSpecialRequest = ruSpecialRequest;
    }

    public static SpecialRequest getSpecialRequest(String requestComfortClass) {
        for (SpecialRequest specialRequest : values()) {
            if (specialRequest.getRuSpecialRequest().equalsIgnoreCase(requestComfortClass)) {
                return specialRequest;
            }
        }
        throw new IllegalArgumentException("Неправильно введено особое пожелание \"" + requestComfortClass + "\"!");
    }

    public String getRuSpecialRequest() {
        return ruSpecialRequest;
    }
}
