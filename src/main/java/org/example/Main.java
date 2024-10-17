package org.example;

import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.example.repository.Repository;
import org.example.util.DatabaseConnection;
import org.example.view.SwingApp;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        SwingApp app = new SwingApp();
        app.setVisible(true);
    }
}