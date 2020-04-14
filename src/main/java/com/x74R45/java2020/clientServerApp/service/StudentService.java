package com.x74R45.java2020.clientServerApp.service;

import com.x74R45.java2020.clientServerApp.model.Student;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StudentService extends Remote {
    List<Student> getStudents() throws RemoteException;
    Student getStudentByName(String name) throws RemoteException;
    Student getStudentById(long id) throws RemoteException;
    void addStudent(String name, int course) throws RemoteException;
    void updateStudent(long id, String name, int course) throws RemoteException;
    void deleteStudent(long id) throws RemoteException;
}