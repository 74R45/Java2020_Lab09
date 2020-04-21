package com.x74R45.java2020.clientServerApp.dao;

import com.x74R45.java2020.clientServerApp.model.Discipline;
import com.x74R45.java2020.clientServerApp.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.util.List;

public class DisciplineDao {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public List<Discipline> getDisciplines() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Discipline> res = session.createQuery("SELECT a FROM Discipline a", Discipline.class).getResultList();
        session.close();
        return res;
    }

    public Discipline getDisciplineByName(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Discipline res = session.createQuery("SELECT a FROM Discipline a WHERE a.name = '"+name+'\'' , Discipline.class).getSingleResult();
        Hibernate.initialize(res.getStudents());
        res.getStudents().forEach((x) -> Hibernate.initialize(x.getStudent()));
        session.close();
        return res;
    }

    public Discipline getDisciplineById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Discipline res = session.load(Discipline.class, id);
        Hibernate.initialize(res.getStudents());
        res.getStudents().forEach((x) -> Hibernate.initialize(x.getStudent()));
        session.close();
        return res;
    }

    public void addDiscipline(String name, BigDecimal credits) {
        Session session = sessionFactory.openSession();
        Discipline newDiscipline = new Discipline(name, credits);
        session.beginTransaction();
        session.save(newDiscipline);
        session.getTransaction().commit();
        session.close();
    }

    public void updateDiscipline(long id, String name, BigDecimal credits) {
        Session session = sessionFactory.openSession();
        Discipline Discipline = new Discipline(id, name, credits);
        session.beginTransaction();
        session.update(Discipline);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteDiscipline(long id) {
        Session session = sessionFactory.openSession();
        Discipline Discipline = session.load(Discipline.class, id);
        session.beginTransaction();
        session.delete(Discipline);
        session.getTransaction().commit();
        session.close();
    }
}