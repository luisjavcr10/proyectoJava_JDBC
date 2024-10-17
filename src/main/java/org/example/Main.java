package org.example;

import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.example.repository.Repository;
import org.example.util.DatabaseConnection;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        try(Connection myCon = DatabaseConnection.getInstance()){
            if(myCon.getAutoCommit()){
                myCon.setAutoCommit(false);
            }

            try{
                Repository<Employee> repository = new EmployeeRepository(myCon);
                Employee employee = new Employee();
                employee.setIdEmployee(9);
                employee.setFirstName("Luis");
                employee.setLastName("Ruiz");
                employee.setCurp("AMFJDV1343KD");
                repository.save(employee);
                myCon.commit();

            } catch (SQLException e) {
                myCon.rollback();
                throw new RuntimeException(e);
            }
        }
    }
}