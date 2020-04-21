package com.x74R45.java2020.clientServerApp.sockets;

import com.x74R45.java2020.clientServerApp.dao.DisciplineDao;
import com.x74R45.java2020.clientServerApp.dao.EnrollmentDao;
import com.x74R45.java2020.clientServerApp.dao.StudentDao;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.Socket;

public class ServerThread implements Runnable {
    private final Socket socket;
    private final StudentDao studentDao;
    private final EnrollmentDao enrollmentDao;
    private final DisciplineDao disciplineDao;

    public ServerThread(Socket socket, StudentDao studentDao, DisciplineDao disciplineDao, EnrollmentDao enrollmentDao) {
        this.socket = socket;
        this.studentDao = studentDao;
        this.disciplineDao = disciplineDao;
        this.enrollmentDao = enrollmentDao;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream())) {
            boolean exit = false;
            while(!exit) {
                String[] command = splitCommand(reader.readLine().trim());
                if (!command[0].equals("exit") && command.length < 2) {
                    os.writeObject("Command failed");
                    os.flush();
                    continue;
                }
                switch (command[0].toLowerCase()) {
                    case "exit":
                        exit = true;
                        socket.close();
                        System.out.println("Client disconnected");
                        break;
                    case "getall":
                        switch (command[1]) {
                            case "student":
                                os.writeObject(studentDao.getStudents());
                                os.flush();
                                break;
                            case "enrollment":
                                os.writeObject(enrollmentDao.getEnrollments());
                                os.flush();
                                break;
                            case "discipline":
                                os.writeObject(disciplineDao.getDisciplines());
                                os.flush();
                                break;
                            default:
                                os.writeObject("Command failed");
                                os.flush();
                        }
                        break;
                    case "getbyid":
                        long id;
                        try {
                            id = Long.parseLong(command[2]);
                        } catch (Exception ignored) {
                            System.out.println("Command failed");
                            break;
                        }
                        switch (command[1]) {
                            case "student":
                                try {
                                    os.writeObject(studentDao.getStudentById(id));
                                } catch (Exception ignored) {
                                    os.writeObject("Not found student with id = " + id);
                                }
                                os.flush();
                                break;
                            case "enrollment":
                                try {
                                    os.writeObject(enrollmentDao.getEnrollmentById(id));
                                } catch (Exception ignored) {
                                    os.writeObject("Not found enrollment with id = " + id);
                                }
                                os.flush();
                                break;
                            case "discipline":
                                try {
                                    os.writeObject(disciplineDao.getDisciplineById(id));
                                } catch (Exception ignored) {
                                    os.writeObject("Not found discipline with id = " + id);
                                }
                                os.flush();
                                break;
                            default:
                                os.writeObject("Command failed");
                                os.flush();
                        }
                        break;
                    case "getbyname":
                        String name;
                        try {
                            name = command[2];
                        } catch (Exception ignored) {
                            System.out.println("Command failed");
                            break;
                        }
                        switch (command[1]) {
                            case "student":
                                try {
                                    os.writeObject(studentDao.getStudentByName(name));
                                } catch (Exception ignored) {
                                    os.writeObject("Not found student \"" + name + "\"");
                                }
                                os.flush();
                                break;
                            case "discipline":
                                try {
                                    os.writeObject(disciplineDao.getDisciplineByName(name));
                                } catch (Exception ignored) {
                                    os.writeObject("Not found discipline \"" + name + "\"");
                                }
                                os.flush();
                                break;
                            default:
                                os.writeObject("Command failed");
                                os.flush();
                        }
                        break;
                    case "add":
                        switch (command[1]) {
                            case "student":
                                int course;
                                try {
                                    name = command[2];
                                    course = Integer.parseInt(command[3]);
                                } catch (Exception ignored) {
                                    System.out.println("Command failed");
                                    break;
                                }
                                try {
                                    studentDao.addStudent(name, course);
                                    os.writeObject("Success");
                                } catch (Exception e) {
                                    os.writeObject("Command failed: " + e.getMessage());
                                }
                                os.flush();
                                break;
                            case "discipline":
                                BigDecimal credits;
                                try {
                                    name = command[2];
                                    credits = new BigDecimal(command[3]);
                                } catch (Exception ignored) {
                                    System.out.println("Command failed");
                                    break;
                                }
                                try {
                                    disciplineDao.addDiscipline(name, credits);
                                    os.writeObject("Success");
                                } catch (Exception e) {
                                    os.writeObject("Command failed: " + e.getMessage());
                                }
                                os.flush();
                                break;
                            case "enrollment":
                                long studentId, disciplineId;
                                int grade;
                                try {
                                    disciplineId = Long.parseLong(command[2]);
                                    studentId = Long.parseLong(command[3]);
                                    grade = Integer.parseInt(command[4]);
                                } catch (Exception ignored) {
                                    System.out.println("Command failed");
                                    break;
                                }
                                try {
                                    enrollmentDao.addEnrollment(disciplineId, studentId, grade);
                                    os.writeObject("Success");
                                } catch (Exception e) {
                                    os.writeObject("Command failed: " + e.getMessage());
                                }
                                os.flush();
                                break;
                            default:
                                os.writeObject("Command failed");
                                os.flush();
                        }
                        break;
                    case "update":
                        switch (command[1]) {
                            case "student":
                                int course;
                                try {
                                    id = Long.parseLong(command[2]);
                                    name = command[3];
                                    course = Integer.parseInt(command[4]);
                                } catch (Exception ignored) {
                                    System.out.println("Command failed");
                                    break;
                                }
                                try {
                                    studentDao.updateStudent(id, name, course);
                                    os.writeObject("Success");
                                } catch (Exception e) {
                                    os.writeObject("Command failed: " + e.getMessage());
                                }
                                os.flush();
                                break;
                            case "discipline":
                                BigDecimal credits;
                                try {
                                    id = Long.parseLong(command[2]);
                                    name = command[3];
                                    credits = new BigDecimal(command[4]);
                                } catch (Exception ignored) {
                                    System.out.println("Command failed");
                                    break;
                                }
                                try {
                                    disciplineDao.updateDiscipline(id, name, credits);
                                    os.writeObject("Success");
                                } catch (Exception e) {
                                    os.writeObject("Command failed: " + e.getMessage());
                                }
                                os.flush();
                                break;
                            case "enrollment":
                                int grade;
                                try {
                                    id = Long.parseLong(command[2]);
                                    grade = Integer.parseInt(command[3]);
                                } catch (Exception ignored) {
                                    System.out.println("Command failed");
                                    break;
                                }
                                try {
                                    enrollmentDao.updateEnrollment(id, grade);
                                    os.writeObject("Success");
                                } catch (Exception e) {
                                    os.writeObject("Command failed: " + e.getMessage());
                                }
                                os.flush();
                                break;
                            default:
                                os.writeObject("Command failed");
                                os.flush();
                        }
                        break;
                    case "delete":
                        try {
                            id = Long.parseLong(command[2]);
                        } catch (Exception ignored) {
                            System.out.println("Command failed");
                            break;
                        }
                        switch (command[1]) {
                            case "student":
                                try {
                                    studentDao.deleteStudent(id);
                                    os.writeObject("Success");
                                } catch (Exception e) {
                                    os.writeObject("Command failed: " + e.getMessage());
                                }
                                os.flush();
                                break;
                            case "discipline":
                                try {
                                    disciplineDao.deleteDiscipline(id);
                                    os.writeObject("Success");
                                } catch (Exception e) {
                                    os.writeObject("Command failed: " + e.getMessage());
                                }
                                os.flush();
                                break;
                            case "enrollment":
                                try {
                                    enrollmentDao.deleteEnrollment(id);
                                    os.writeObject("Success");
                                } catch (Exception e) {
                                    os.writeObject("Command failed: " + e.getMessage());
                                }
                                os.flush();
                                break;
                            default:
                                os.writeObject("Command failed");
                                os.flush();
                        }
                        break;
                    default:
                        os.writeObject("Command failed");
                        os.flush();
                }
            }
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ignored) {}
            e.printStackTrace();
        }
    }

    private static String[] splitCommand(String s) {
        String[] res1 = s.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        String[] res2 = new String[res1.length];
        for (int i = 0; i < res1.length; i++)
            res2[i] = res1[i].replace('"', ' ').trim();
        return res2;
    }
}