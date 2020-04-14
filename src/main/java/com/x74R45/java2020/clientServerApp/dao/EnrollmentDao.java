package com.x74R45.java2020.clientServerApp.dao;

import com.x74R45.java2020.clientServerApp.model.Discipline;
import com.x74R45.java2020.clientServerApp.model.Enrollment;
import com.x74R45.java2020.clientServerApp.model.Student;
import com.x74R45.java2020.clientServerApp.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class EnrollmentDao {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public List<Enrollment> getEnrollments() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Enrollment> res = session.createQuery("SELECT a FROM Enrollment a", Enrollment.class).getResultList();
        res.forEach((x) -> {
            Hibernate.initialize(x.getDiscipline());
            Hibernate.initialize(x.getStudent());
        });
        session.close();
        return res;
    }

    public Enrollment getEnrollmentById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Enrollment res = session.load(Enrollment.class, id);
        Hibernate.initialize(res.getDiscipline());
        Hibernate.initialize(res.getStudent());
        session.close();
        return res;
    }

    public void addEnrollment(long id_discipline, long id_student, int grade) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Discipline discipline = session.load(Discipline.class, id_discipline);
        Student student = session.load(Student.class, id_student);
        Enrollment newEnrollment = new Enrollment(discipline, student, grade);
        session.save(newEnrollment);
        session.getTransaction().commit();
        session.close();
    }

    public void updateEnrollment(long id, int grade) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Enrollment enrollment = session.load(Enrollment.class, id);
        enrollment.setGrade(grade);
        session.update(enrollment);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteEnrollment(long id) {
        Session session = sessionFactory.openSession();
        Enrollment Enrollment = session.load(Enrollment.class, id);
        session.beginTransaction();
        session.delete(Enrollment);
        session.getTransaction().commit();
        session.close();
    }
}