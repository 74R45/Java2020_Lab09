package com.x74R45.java2020.clientServerApp;

import com.x74R45.java2020.clientServerApp.model.Discipline;
import com.x74R45.java2020.clientServerApp.model.Enrollment;
import com.x74R45.java2020.clientServerApp.model.Student;
import com.x74R45.java2020.clientServerApp.service.DisciplineService;
import com.x74R45.java2020.clientServerApp.service.EnrollmentService;
import com.x74R45.java2020.clientServerApp.service.StudentService;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry();

        // Testing Students
        System.out.println("---Testing Students---");
        StudentService studentService = (StudentService) reg.lookup("rmi:students");
        List<Student> students = studentService.getStudents();
        System.out.println("Getting all students:");
        students.forEach(System.out::println);

        Student st = studentService.getStudentByName(students.get(0).getName());
        System.out.println("Getting student by name: " + st);
        System.out.println("Their disciplines: ");
        st.getDisciplines().forEach((x) -> System.out.println("{" + x.getDiscipline().getName() + ", " + x.getGrade() + "}"));
        System.out.println("Getting student by id: " + studentService.getStudentById(students.get(0).getId()));

        studentService.addStudent("Someone Else", 1);
        Student createdStudent = studentService.getStudentByName("Someone Else");
        System.out.println("Created \"Someone Else\" who's on 1st course: " + createdStudent);

        studentService.updateStudent(createdStudent.getId(), createdStudent.getName(), 2);
        System.out.println("Now he's on 2nd course: " + studentService.getStudentById(createdStudent.getId()));

        studentService.deleteStudent(createdStudent.getId());
        System.out.println("The created student is now deleted. Here's all students: " + studentService.getStudents());

        // Testing Disciplines
        System.out.println("\n---Testing Disciplines---");
        DisciplineService disciplineService = (DisciplineService) reg.lookup("rmi:disciplines");
        List<Discipline> disciplines = disciplineService.getDisciplines();
        System.out.println("Getting all disciplines:");
        disciplines.forEach(System.out::println);

        Discipline dis = disciplineService.getDisciplineByName(disciplines.get(0).getName());
        System.out.println("Getting discipline by name: " + dis);
        System.out.println("Students who enrolled in this course: ");
        dis.getStudents().forEach((x) -> System.out.println("{" + x.getStudent().getName() + ", " + x.getGrade() + "}"));
        System.out.println("Getting discipline by id: " + disciplineService.getDisciplineById(disciplines.get(0).getId()));

        disciplineService.addDiscipline("Functional Programming", new BigDecimal(4));
        Discipline createdDiscipline = disciplineService.getDisciplineByName("Functional Programming");
        System.out.println("Created \"Functional Programming\" with 4 credits: " + createdDiscipline);

        disciplineService.updateDiscipline(createdDiscipline.getId(), "ProgrammingInHaskell", createdDiscipline.getCredits());
        System.out.println("Now it's called ProgrammingInHaskell: " + disciplineService.getDisciplineById(createdDiscipline.getId()));

        disciplineService.deleteDiscipline(createdDiscipline.getId());
        System.out.println("The created discipline is now deleted. Here's all disciplines: " + disciplineService.getDisciplines());

        // Testing Enrollments
        System.out.println("\n---Testing Enrollments---");
        EnrollmentService enrollmentService = (EnrollmentService) reg.lookup("rmi:enrollments");
        List<Enrollment> enrollments = enrollmentService.getEnrollments();
        System.out.println("Getting all Enrollments:");
        enrollments.forEach(System.out::println);

        Enrollment en = enrollmentService.getEnrollmentById(enrollments.get(0).getId());
        System.out.println("Getting Enrollment by id: " + enrollmentService.getEnrollmentById(enrollments.get(0).getId()));

        studentService.addStudent("Ivanko", 3);
        disciplineService.addDiscipline("Databases", new BigDecimal("4.5"));
        Student ivanko = studentService.getStudentByName("Ivanko");
        Discipline databases = disciplineService.getDisciplineByName("Databases");
        enrollmentService.addEnrollment(databases.getId(), ivanko.getId(), 61);
        Enrollment createdEnrollment = studentService.getStudentByName("Ivanko").getDisciplines().get(0);
        System.out.println("Created a discipline Databases and a new student Ivanko; enrolled him in it: " + createdEnrollment);

        enrollmentService.updateEnrollment(createdEnrollment.getId(), 100);
        System.out.println("Now Ivanko finished Databases a lot better: " + studentService.getStudentByName("Ivanko").getDisciplines().get(0));

        enrollmentService.deleteEnrollment(createdEnrollment.getId());
        System.out.println("The created Enrollment is now deleted. Here's all Enrollments: " + enrollmentService.getEnrollments());
        studentService.deleteStudent(ivanko.getId());
        disciplineService.deleteDiscipline(databases.getId());
    }
}