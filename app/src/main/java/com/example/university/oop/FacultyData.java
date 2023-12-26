package com.example.university.oop;

import androidx.annotation.NonNull;

public class FacultyData {

    private int id;
    private String name;

    public FacultyData(int id, String name) {
        this.id = id;
        this.name = name;
        if(name==null)
        {
            name="";
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return getName().toString();
    }
}
