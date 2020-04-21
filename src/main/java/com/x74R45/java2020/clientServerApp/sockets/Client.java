package com.x74R45.java2020.clientServerApp.sockets;

import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.x74R45.java2020.clientServerApp.sockets.ServerStarter.PORT;

public class Client {
    public static void main(String[] args) {
        Socket clientSocket = null;
        try {
            clientSocket = new Socket("localhost", PORT);
        } catch (UnknownHostException ignored) {
            System.out.println("No such host");
            System.exit(0);
        } catch (IOException ignored) {
            System.out.println("Error while establishing connection");
            System.exit(0);
        }
        System.out.println("Connection established");

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
             ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            boolean exit = false;
            while (!exit) {
                System.out.print("> ");
                String command = reader.readLine().trim();
                String[] commandSplit = splitCommand(command);
                if (!commandSplit[0].equals("help") && !commandSplit[0].equals("exit") && commandSplit.length < 2) {
                    System.out.println("Type \"help\" to get all commands");
                    continue;
                }
                switch (commandSplit[0].toLowerCase()) {
                    case "help":
                        System.out.println(
                            "help                                         -- print help\n" +
                            "exit                                         -- stop session\n" +
                            "getall {student/enrollment/discipline}       -- get all data\n" +
                            "getbyid {student/enrollment/discipline} {id} -- get data by id\n" +
                            "getbyname {student/discipline} {name}        -- get data by name\n" +
                            "add                                          -- add data\n" +
                            "    student {name} {course}\n" +
                            "    enrollment {discipline id} {student id} {grade}\n" +
                            "    discipline {name} {credits}\n" +
                            "update                                       -- update data by id\n" +
                            "       student {id} {name} {course}\n" +
                            "       enrollment {id} {grade}\n" +
                            "       discipline {id} {name} {credits}\n" +
                            "delete {student/enrollment/discipline} {id}  -- delete data by id");
                        break;
                    case "exit":
                        exit = true;
                        writer.println(command);
                        writer.flush();
                        break;
                    case "getall":
                        switch (commandSplit[1]) {
                            case "student":
                            case "enrollment":
                            case "discipline":
                                writer.println(command);
                                writer.flush();
                                System.out.println(is.readObject());
                                break;
                            default: System.out.println("Syntax: getall {student/enrollment/discipline}");
                        }
                        break;
                    case "getbyid":
                        switch (commandSplit[1]) {
                            case "student":
                            case "enrollment":
                            case "discipline":
                                try {
                                    Long.parseLong(commandSplit[2]);
                                } catch (Exception ignored) {
                                    System.out.println("Syntax: getbyid {student/enrollment/discipline} {id}");
                                    break;
                                }
                                writer.println(command);
                                writer.flush();
                                System.out.println(is.readObject());
                                break;
                            default: System.out.println("Syntax: getbyid {student/enrollment/discipline} {id}");
                        }
                        break;
                    case "getbyname":
                        switch (commandSplit[1]) {
                            case "student":
                            case "discipline":
                                if (commandSplit.length < 3) {
                                    System.out.println("Syntax: getbyname {student/discipline} {name}");
                                    break;
                                }
                                writer.println(command);
                                writer.flush();
                                System.out.println(is.readObject());
                                break;
                            default: System.out.println("Syntax: getbyname {student/discipline} {name}");
                        }
                        break;
                    case "add":
                        switch (commandSplit[1]) {
                            case "student":
                                try {
                                    Integer.parseInt(commandSplit[3]);
                                } catch (Exception ignored) {
                                    System.out.println("Syntax: add student {name} {course}");
                                    break;
                                }
                                writer.println(command);
                                writer.flush();
                                System.out.println(is.readObject());
                                break;
                            case "enrollment":
                                try {
                                    Long.parseLong(commandSplit[2]);
                                    Long.parseLong(commandSplit[3]);
                                    Integer.parseInt(commandSplit[4]);
                                } catch (Exception ignored) {
                                    System.out.println("Syntax: add enrollment {discipline id} {student id} {grade}");
                                    break;
                                }
                                writer.println(command);
                                writer.flush();
                                System.out.println(is.readObject());
                                break;
                            case "discipline":
                                try {
                                    new BigDecimal(commandSplit[3]);
                                } catch (Exception ignored) {
                                    System.out.println("Syntax: add discipline {name} {credits}");
                                    break;
                                }
                                writer.println(command);
                                writer.flush();
                                System.out.println(is.readObject());
                                break;
                            default: System.out.println("Syntax: add {student/enrollment/discipline} {properties}");
                        }
                        break;
                    case "update":
                        switch (commandSplit[1]) {
                            case "student":
                                try {
                                    Long.parseLong(commandSplit[2]);
                                    Integer.parseInt(commandSplit[4]);
                                } catch (Exception ignored) {
                                    System.out.println("Syntax: update student {id} {name} {course}");
                                    break;
                                }
                                writer.println(command);
                                writer.flush();
                                System.out.println(is.readObject());
                                break;
                            case "enrollment":
                                try {
                                    Long.parseLong(commandSplit[2]);
                                    Integer.parseInt(commandSplit[3]);
                                } catch (Exception ignored) {
                                    System.out.println("Syntax: update enrollment {id} {grade}");
                                    break;
                                }
                                writer.println(command);
                                writer.flush();
                                System.out.println(is.readObject());
                                break;
                            case "discipline":
                                try {
                                    Long.parseLong(commandSplit[2]);
                                    new BigDecimal(commandSplit[4]);
                                } catch (Exception ignored) {
                                    System.out.println("Syntax: update discipline {id} {name} {credits}");
                                    break;
                                }
                                writer.println(command);
                                writer.flush();
                                System.out.println(is.readObject());
                                break;
                            default: System.out.println("Syntax: update {student/enrollment/discipline} {properties}");
                        }
                        break;
                    case "delete":
                        switch (commandSplit[1]) {
                            case "student":
                            case "enrollment":
                            case "discipline":
                                try {
                                    Long.parseLong(commandSplit[2]);
                                } catch (Exception ignored) {
                                    System.out.println("Syntax: delete {student/enrollment/discipline} {id}");
                                    break;
                                }
                                writer.println(command);
                                writer.flush();
                                System.out.println(is.readObject());
                                break;
                            default: System.out.println("Syntax: delete {student/enrollment/discipline} {id}");
                        }
                        break;
                    default: System.out.println("Type \"help\" to get all commands");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Fatal error!");
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