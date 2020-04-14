package com.x74R45.java2020.clientServerApp.service;

import com.x74R45.java2020.clientServerApp.model.Discipline;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DisciplineService extends Remote {
    List<Discipline> getDisciplines() throws RemoteException;
    Discipline getDisciplineByName(String name) throws RemoteException;
    Discipline getDisciplineById(long id) throws RemoteException;
    void addDiscipline(String name, BigDecimal credits) throws RemoteException;
    void updateDiscipline(long id, String name, BigDecimal credits) throws RemoteException;
    void deleteDiscipline(long id) throws RemoteException;
}