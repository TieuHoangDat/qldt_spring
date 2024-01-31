package com.ptit.qldt.models;
//import jakarta.persistence.*;
import javax.persistence.*;

@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @Column(name = "course_id")
    private String id;
    @Column(name = "course_name")
    private String name;
    private int num_credit;
    private int term;
    private int notcal;

    public Course(String id, String name, int num_credit, int term, int notcal) {
        this.id = id;
        this.name = name;
        this.num_credit = num_credit;
        this.term = term;
        this.notcal = notcal;
    }

    public Course() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum_credit() {
        return num_credit;
    }

    public void setNum_credit(int num_credit) {
        this.num_credit = num_credit;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getNotcal() {
        return notcal;
    }

    public void setNotcal(int notcal) {
        this.notcal = notcal;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", num_credit=" + num_credit +
                ", term=" + term +
                ", notcal=" + notcal +
                '}';
    }
}
