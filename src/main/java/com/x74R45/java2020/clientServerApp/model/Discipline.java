package com.x74R45.java2020.clientServerApp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "disciplines")
public class Discipline implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "credits", length = 3)
    private BigDecimal credits;

    @OneToMany(mappedBy = "discipline")
    private List<Enrollment> students;

    public Discipline() {}

    public Discipline(String name, BigDecimal credits) {
        this.name = name;
        this.credits = credits;
    }

    public Discipline(long id, String name, BigDecimal credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
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

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public List<Enrollment> getStudents() {
        return students;
    }

    public void addStudent(Student student, int grade) {
        Enrollment enrollment = new Enrollment(this, student, grade);
        students.add(enrollment);
        student.getDisciplines().add(enrollment);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                '}';
    }
}