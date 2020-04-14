package com.x74R45.java2020.clientServerApp.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "enrollment")
public class Enrollment implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_discipline", nullable = false)
    private Discipline discipline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_student", nullable = false)
    private Student student;

    @Column(name = "grade", length = 3)
    private int grade;

    public Enrollment() {}

    public Enrollment(Discipline discipline, Student student, int grade) {
        this.discipline = discipline;
        this.student = student;
        this.grade = grade;
    }

    public Enrollment(long id, Discipline discipline, Student student, int grade) {
        this.id = id;
        this.discipline = discipline;
        this.student = student;
        this.grade = grade;
    }

    public long getId() {
        return id;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", " + discipline +
                ", " + student +
                ", grade=" + grade +
                '}';
    }
}