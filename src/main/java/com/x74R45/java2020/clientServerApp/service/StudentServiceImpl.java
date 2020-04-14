package com.x74R45.java2020.clientServerApp.service;

import com.x74R45.java2020.clientServerApp.dao.StudentDao;
import com.x74R45.java2020.clientServerApp.model.Student;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class StudentServiceImpl extends UnicastRemoteObject implements StudentService {
    private final StudentDao studentDao = new StudentDao();

    public StudentServiceImpl() throws RemoteException {
    }

    @Override
    public List<Student> getStudents() throws RemoteException {
        return studentDao.getStudents();
    }

    @Override
    public Student getStudentByName(String name) throws RemoteException {
        return studentDao.getStudentByName(name);
    }

    @Override
    public Student getStudentById(long id) throws RemoteException {
        return studentDao.getStudentById(id);
    }

    @Override
    public void addStudent(String name, int course) throws RemoteException {
        studentDao.addStudent(name, course);
    }

    @Override
    public void updateStudent(long id, String name, int course) throws RemoteException {
        studentDao.updateStudent(id, name, course);
    }

    @Override
    public void deleteStudent(long id) throws RemoteException {
        studentDao.deleteStudent(id);
    }
}