package com.x74R45.java2020.clientServerApp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "student")
public class Student implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", length = 90)
    private String name;

    @Column(name = "course", length = 11)
    private int course;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> disciplines;

    public Student() {}

    public Student(String name, int course) {
        this.name = name;
        this.course = course;
    }

    public Student(long id, String name, int course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public List<Enrollment> getDisciplines() {
        return disciplines;
    }

    public void addDisciplines(Discipline discipline, int grade) {
        Enrollment enrollment = new Enrollment(discipline, this, grade);
        disciplines.add(enrollment);
        discipline.getStudents().add(enrollment);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", course=" + course + '}';
    }
}