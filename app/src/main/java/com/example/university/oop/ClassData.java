package com.example.university.oop;

public class ClassData {
    private int id,faculty_id;
    private String type;

    public ClassData(int id, int faculty_id, String type) {
        this.id = id;
        this.faculty_id = faculty_id;
        this.type = type;
    }

    public int getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(int faculty_id) {
        this.faculty_id = faculty_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return getType() + " _ "+getId();
    }
}
