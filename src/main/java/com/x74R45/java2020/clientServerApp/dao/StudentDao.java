package com.x74R45.java2020.clientServerApp.dao;

import com.x74R45.java2020.clientServerApp.util.HibernateUtil;
import com.x74R45.java2020.clientServerApp.model.Student;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class StudentDao {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public List<Student> getStudents() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Student> res = session.createQuery("SELECT a FROM Student a", Student.class).getResultList();
        session.close();
        return res;
    }

    public Student getStudentByName(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Student res = session.createQuery("SELECT a FROM Student a WHERE a.name = '"+name+'\'' , Student.class).getSingleResult();
        Hibernate.initialize(res.getDisciplines());
        res.getDisciplines().forEach((x) -> Hibernate.initialize(x.getDiscipline()));
        session.close();
        return res;
    }

    public Student getStudentById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Student res = session.load(Student.class, id);
        Hibernate.initialize(res.getDisciplines());
        res.getDisciplines().forEach((x) -> Hibernate.initialize(x.getDiscipline()));
        session.close();
        return res;
    }

    public void addStudent(String name, int course) {
        Session session = sessionFactory.openSession();
        Student newStudent = new Student(name, course);
        session.beginTransaction();
        session.save(newStudent);
        session.getTransaction().commit();
        session.close();
    }

    public void updateStudent(long id, String name, int course) {
        Session session = sessionFactory.openSession();
        Student student = new Student(id, name, course);
        session.beginTransaction();
        session.update(student);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteStudent(long id) {
        Session session = sessionFactory.openSession();
        Student student = session.load(Student.class, id);
        session.beginTransaction();
        session.delete(student);
        session.getTransaction().commit();
        session.close();
    }
}