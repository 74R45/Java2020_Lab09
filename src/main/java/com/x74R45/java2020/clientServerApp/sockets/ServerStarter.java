package com.x74R45.java2020.clientServerApp.sockets;

import com.x74R45.java2020.clientServerApp.dao.DisciplineDao;
import com.x74R45.java2020.clientServerApp.dao.EnrollmentDao;
import com.x74R45.java2020.clientServerApp.dao.StudentDao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class ServerStarter {
    public static final int PORT = 1099;

    public static void main(String[] args) {
        StudentDao sDao = new StudentDao();
        DisciplineDao dDao = new DisciplineDao();
        EnrollmentDao eDao = new EnrollmentDao();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Error in binding to port " + PORT);
            System.exit(-1);
        }
        System.out.println("Server registered at port " + PORT);

        while (true) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                new Thread(new ServerThread(socket, sDao, dDao, eDao)).start();
                System.out.println("New client connected");
            } catch (IOException e) {
                System.out.println("Attempt of new connection failed");
            }
        }
    }
}