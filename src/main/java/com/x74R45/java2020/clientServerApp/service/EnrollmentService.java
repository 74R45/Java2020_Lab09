package com.x74R45.java2020.clientServerApp.service;

import com.x74R45.java2020.clientServerApp.model.Enrollment;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface EnrollmentService extends Remote {
    List<Enrollment> getEnrollments() throws RemoteException;
    Enrollment getEnrollmentById(long id) throws RemoteException;
    void addEnrollment(long id_discipline, long id_student, int grade) throws RemoteException;
    void updateEnrollment(long id, int grade) throws RemoteException;
    void deleteEnrollment(long id) throws RemoteException;
}