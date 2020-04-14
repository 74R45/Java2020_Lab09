package com.x74R45.java2020.clientServerApp.service;

import com.x74R45.java2020.clientServerApp.dao.EnrollmentDao;
import com.x74R45.java2020.clientServerApp.model.Enrollment;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class EnrollmentServiceImpl extends UnicastRemoteObject implements EnrollmentService {
    private final EnrollmentDao EnrollmentDao = new EnrollmentDao();

    public EnrollmentServiceImpl() throws RemoteException {
    }

    @Override
    public List<Enrollment> getEnrollments() throws RemoteException {
        return EnrollmentDao.getEnrollments();
    }

    @Override
    public Enrollment getEnrollmentById(long id) throws RemoteException {
        return EnrollmentDao.getEnrollmentById(id);
    }

    @Override
    public void addEnrollment(long id_discipline, long id_student, int grade) throws RemoteException {
        EnrollmentDao.addEnrollment(id_discipline, id_student, grade);
    }

    @Override
    public void updateEnrollment(long id, int grade) throws RemoteException {
        EnrollmentDao.updateEnrollment(id, grade);
    }

    @Override
    public void deleteEnrollment(long id) throws RemoteException {
        EnrollmentDao.deleteEnrollment(id);
    }
}