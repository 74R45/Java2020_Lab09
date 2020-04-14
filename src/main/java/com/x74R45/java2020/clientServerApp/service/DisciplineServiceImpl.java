package com.x74R45.java2020.clientServerApp.service;

import com.x74R45.java2020.clientServerApp.dao.DisciplineDao;
import com.x74R45.java2020.clientServerApp.model.Discipline;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DisciplineServiceImpl extends UnicastRemoteObject implements DisciplineService {
    private final DisciplineDao disciplineDao = new DisciplineDao();

    public DisciplineServiceImpl() throws RemoteException {
    }

    @Override
    public List<Discipline> getDisciplines() throws RemoteException {
        return disciplineDao.getDisciplines();
    }

    @Override
    public Discipline getDisciplineByName(String name) throws RemoteException {
        return disciplineDao.getDisciplineByName(name);
    }

    @Override
    public Discipline getDisciplineById(long id) throws RemoteException {
        return disciplineDao.getDisciplineById(id);
    }

    @Override
    public void addDiscipline(String name, BigDecimal credits) throws RemoteException {
        disciplineDao.addDiscipline(name, credits);
    }

    @Override
    public void updateDiscipline(long id, String name, BigDecimal credits) throws RemoteException {
        disciplineDao.updateDiscipline(id, name, credits);
    }

    @Override
    public void deleteDiscipline(long id) throws RemoteException {
        disciplineDao.deleteDiscipline(id);
    }
}