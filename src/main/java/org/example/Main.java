package org.example;

import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.example.repository.Repository;
import org.example.util.DatabaseConnection;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Repository<Employee> repository = new EmployeeRepository();
        repository.findAll().forEach(System.out::println);
        System.out.println("Buscar por id");
        System.out.println(repository.getById(10));

    }
}