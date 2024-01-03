package com.example.university.oop;

import java.io.Serializable;

public class StudentData implements Serializable {

    private String name,gender,Nationality,city,country,Religion;
    private int id,dep_id,group_id,gpa;


    public StudentData(String name, String gender, String nationality, String city, String country, String religion, int id, int dep_id, int group_id, int gpa) {
        this.name = name;
        this.gender = gender;
        Nationality = nationality;
        this.city = city;
        this.country = country;
        Religion = religion;
        this.id = id;
        this.dep_id = dep_id;
        this.group_id = group_id;
        this.gpa = gpa;
    }

    public String getReligion() {
        return Religion;
    }

    public void setReligion(String religion) {
        Religion = religion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDep_id() {
        return dep_id;
    }

    public void setDep_id(int dep_id) {
        this.dep_id = dep_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getGpa() {
        return gpa;
    }

    public void setGpa(int gpa) {
        this.gpa = gpa;
    }
}
