package com.example.university.oop;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class CourseData implements Serializable {
    int crs_code;
    String crs_name;
    String crs_desc;
    int credits;

    public CourseData(int crs_code, String crs_name, String crs_desc, int credits) {
        this.crs_code = crs_code;
        this.crs_name = crs_name;
        this.crs_desc = crs_desc;
        this.credits = credits;
    }

    public int getCrs_code() {
        return crs_code;
    }

    public void setCrs_code(int crs_code) {
        this.crs_code = crs_code;
    }

    public String getCrs_name() {
        return crs_name;
    }

    public void setCrs_name(String crs_name) {
        this.crs_name = crs_name;
    }

    public String getCrs_desc() {
        return crs_desc;
    }

    public void setCrs_desc(String crs_desc) {
        this.crs_desc = crs_desc;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @NonNull
    @Override
    public String toString() {
        return getCrs_name();
    }
}
