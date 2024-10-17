package org.example;

import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.example.repository.Repository;
import org.example.util.DatabaseConnection;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //We use try-with-resources because it automatically closes the resources.
        try(Connection myConn = DatabaseConnection.getInstance())
        {
            Repository<Employee> repository = new EmployeeRepository();

            System.out.println("Eliminando un empleado");
            Employee e = new Employee();
            e=repository.getById(5);
            repository.delete(e);
        }


    }
}