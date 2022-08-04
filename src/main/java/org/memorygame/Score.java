package org.memorygame;

import java.io.Serializable;
import java.time.LocalDate;


public class Score implements Serializable {
    private String name;
    private LocalDate time;
    private long guessingTime;
    private int guessingTries;

    public Score() {
    }
    public Score(String name, LocalDate date, long guessingTime, int guessingTries) {
        this.name = name;
        this.time = date;
        this.guessingTime = guessingTime;
        this.guessingTries = guessingTries;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    LocalDate getTime() {
        return time;
    }

    void setTime(LocalDate time) {
        this.time = time;
    }

    long getGuessingTime() {
        return guessingTime;
    }

    void setGuessingTime(long guessingTime) {
        this.guessingTime = guessingTime;
    }

    int getGuessingTries() {
        return guessingTries;
    }

    void setGuessingTries(int guessingTries) {
        this.guessingTries = guessingTries;
    }

    @Override
    public String toString() {
        return "Score{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", guessingTime=" + guessingTime +
                ", guessingTries=" + guessingTries +
                '}';
    }
}
