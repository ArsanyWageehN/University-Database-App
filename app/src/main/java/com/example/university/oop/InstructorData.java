package com.example.university.oop;

import java.io.Serializable;

public class InstructorData implements Serializable {
    private String Job_Tittle,Full_Name;
    private int Instructor_id,Salary;

    public InstructorData(String job_Tittle, String full_Name, int instructor_id, int salary) {
        Job_Tittle = job_Tittle;
        Full_Name = full_Name;
        Instructor_id = instructor_id;
        Salary = salary;
    }

    public String getJob_Tittle() {
        return Job_Tittle;
    }

    public void setJob_Tittle(String job_Tittle) {
        Job_Tittle = job_Tittle;
    }

    public String getFull_Name() {
        return Full_Name;
    }

    public void setFull_Name(String full_Name) {
        Full_Name = full_Name;
    }

    public int getInstructor_id() {
        return Instructor_id;
    }

    public void setInstructor_id(int instructor_id) {
        Instructor_id = instructor_id;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }
}
