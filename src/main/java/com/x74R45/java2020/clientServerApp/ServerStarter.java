package com.x74R45.java2020.clientServerApp;

import com.x74R45.java2020.clientServerApp.service.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerStarter {
    public static void main(String[] args) throws RemoteException {
        StudentService studentService = new StudentServiceImpl();
        DisciplineService disciplineService = new DisciplineServiceImpl();
        EnrollmentService enrollmentService = new EnrollmentServiceImpl();

        LocateRegistry.createRegistry(1099);
        Registry reg = LocateRegistry.getRegistry();
        reg.rebind("rmi:students", studentService);
        reg.rebind("rmi:disciplines", disciplineService);
        reg.rebind("rmi:enrollments", enrollmentService);

        System.out.println("Server has started.");
    }
}