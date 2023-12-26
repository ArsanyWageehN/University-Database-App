package com.example.university.oop;

import androidx.annotation.NonNull;

public class DepartmentData {

    private int id,faculty_id;
    private String name;

    public DepartmentData(int id, int faculty_id, String name) {
        this.id = id;
        this.faculty_id = faculty_id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(int faculty_id) {
        this.faculty_id = faculty_id;
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
        return getName()+"";
    }
}
