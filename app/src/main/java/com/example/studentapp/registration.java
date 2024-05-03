package com.example.studentapp;

public class registration {
    private String roll;
    private String name;
    private String course;
    private String branch;
    private int year;
    private int semester;
    private String section;

    public registration(String roll, String name, String course, String branch, int year, int semester, String section) {
        this.roll = roll;
        this.name = name;
        this.course = course;
        this.branch = branch;
        this.year = year;
        this.semester = semester;
        this.section = section;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
