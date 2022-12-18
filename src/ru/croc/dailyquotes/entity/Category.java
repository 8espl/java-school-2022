package ru.croc.dailyquotes.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Category {
    @JsonProperty("death")
    DEATH,
    @JsonProperty("happiness")
    HAPPINESS,
    @JsonProperty("inspiration")
    INSPIRATION,
    @JsonProperty("love")
    LOVE,
    @JsonProperty("poetry")
    POETRY,
    @JsonProperty("romance")
    ROMANCE,
    @JsonProperty("science")
    SCIENCE,
    @JsonProperty("success")
    SUCCESS,
    @JsonProperty("time")
    TIME,
    @JsonProperty("truth")
    TRUTH;
}
