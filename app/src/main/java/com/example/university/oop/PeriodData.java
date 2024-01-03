package com.example.university.oop;

import androidx.annotation.NonNull;

public class PeriodData {

    private int id;
    private String from,to;

    public PeriodData(int id, String from, String to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @NonNull
    @Override
    public String toString() {
        return "From='" + from + '\'' +
                " To='" + to;
    }
}
